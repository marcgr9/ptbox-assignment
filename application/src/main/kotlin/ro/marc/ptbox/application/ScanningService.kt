package ro.marc.ptbox.application

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ro.marc.ptbox.shared.domain.ports.CompletedScansRepository
import ro.marc.ptbox.shared.domain.model.Scan
import ro.marc.ptbox.shared.domain.ports.ScannerPort
import ro.marc.ptbox.shared.domain.ports.ScansRepository
import ro.marc.ptbox.shared.domain.validator.WebsiteValidator
import ro.marc.ptbox.shared.dto.FindScansQuery
import java.util.*

class ScanningService(
    private val scannerPort: ScannerPort,
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

    suspend fun runAmass(website: String): Scan {
        if (!WebsiteValidator.validate(website)) {
            throw IllegalArgumentException()
        }

        val scan = Scan(
            id = generateTaskId(),
//            type = Scan.Type.AMASS,
            website = website,
            status = Scan.Status.PENDING,
            results = listOf(),
        )

        val persistedScan = scansRepository.create(scan)

        scannerPort.processWebsite(scan)

        return persistedScan
    }

    suspend fun findScans(searchParams: FindScansQuery): List<Scan> {
        return scansRepository.findByStatusIn(searchParams.status)
    }

    private fun generateTaskId(): UUID = UUID.randomUUID()

}
