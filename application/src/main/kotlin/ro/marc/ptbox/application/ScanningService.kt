package ro.marc.ptbox.application

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ro.marc.ptbox.shared.domain.CompletedScansRepository
import ro.marc.ptbox.shared.domain.Scan
import ro.marc.ptbox.shared.domain.ScanAdapter
import ro.marc.ptbox.shared.domain.ScansRepository
import java.util.*

class ScanningService(
    private val scanAdapter: ScanAdapter,
    private val completedScansEventRepository: CompletedScansRepository,
    private val scansRepository: ScansRepository,
) {

    private val _scanCompletedEvent = MutableSharedFlow<Scan>(extraBufferCapacity = 1)
    val scanCompletedEvent: Flow<Scan>
        get() = _scanCompletedEvent.asSharedFlow()

    init {
        CoroutineScope(SupervisorJob()).launch {
            completedScansEventRepository.scans.collect {
                val scan = scansRepository.update(it)
                _scanCompletedEvent.tryEmit(scan)
            }
        }
    }

    suspend fun runAmass(website: String): UUID {
        val scan = Scan(
            id = generateTaskId(),
//            type = Scan.Type.AMASS,
            website = website,
            status = Scan.Status.PENDING,
            results = listOf(),
        )

        scansRepository.create(scan)

        scanAdapter.processWebsite(scan)

        return scan.id
    }

    private fun generateTaskId(): UUID = UUID.randomUUID();

}
