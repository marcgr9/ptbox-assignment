package ro.marc.ptbox.application

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ro.marc.ptbox.shared.domain.CompletedScansRepository
import ro.marc.ptbox.shared.domain.Scan
import ro.marc.ptbox.shared.domain.ScanAdapter
import java.util.*

class ScanningService(
    private val scanAdapter: ScanAdapter,
    private val scansRepository: CompletedScansRepository,
) {

    private val _scanCompletedEvent = MutableSharedFlow<Scan>(replay = 1)
    val scanCompletedEvent: Flow<Scan>
        get() = _scanCompletedEvent.asSharedFlow()

    init {
        CoroutineScope(SupervisorJob()).launch {
            scansRepository.scans.collect {
                val scan = saveScanInDb(it)
                _scanCompletedEvent.tryEmit(scan)
            }
        }
    }

    fun runAmass(website: String): UUID {
        val scan = Scan(
            id = generateTaskId(),
            type = Scan.Type.AMASS,
            website = website,
            results = null,
        )
        scanAdapter.processWebsite(scan)

        return scan.id
    }

    private suspend fun saveScanInDb(scan: Scan): Scan {
        // save in db when the adapter is implemented
        return scan
    }

    private fun generateTaskId(): UUID = UUID.randomUUID();

}
