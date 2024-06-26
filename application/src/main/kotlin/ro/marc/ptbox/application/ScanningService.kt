package ro.marc.ptbox.application

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import ro.marc.ptbox.shared.domain.ports.CompletedScansRepository
import ro.marc.ptbox.shared.domain.model.Scan
import ro.marc.ptbox.shared.domain.ports.ScannerPort
import ro.marc.ptbox.shared.domain.ports.ScansRepository
import ro.marc.ptbox.shared.domain.validator.WebsiteValidator
import ro.marc.ptbox.shared.dto.FindScansQuery
import java.util.*
import kotlin.coroutines.coroutineContext

class ScanningService(
    private val amassPort: ScannerPort,
    private val theHarvesterPort: ScannerPort,
    private val completedScansEventRepository: CompletedScansRepository,
    private val scansRepository: ScansRepository,
) {

    private val _scanCompletedEvent = MutableSharedFlow<Scan>(extraBufferCapacity = 1)
    val scanCompletedEvent: Flow<Scan>
        get() = _scanCompletedEvent.asSharedFlow()

    private val threadSpawningScope = CoroutineScope(Dispatchers.IO)

    init {
        CoroutineScope(SupervisorJob()).launch {
            completedScansEventRepository.scans.collect {
                val scan = scansRepository.update(it)
                _scanCompletedEvent.tryEmit(scan)
            }
        }
    }

    suspend fun scanDomain(website: String, type: Scan.Type): Scan {
        if (!WebsiteValidator.validate(website)) {
            throw IllegalArgumentException()
        }

        val scan = Scan(
            id = generateTaskId(),
            type = type,
            website = website,
            status = Scan.Status.PENDING,
            results = Scan.Results.empty(),
        )

        val persistedScan = scansRepository.create(scan)

        threadSpawningScope.launch {
            val processFn = when (type) {
                Scan.Type.AMASS -> amassPort::processWebsite
                Scan.Type.THE_HARVESTER -> theHarvesterPort::processWebsite
            }
            processFn(scan)
        }

        return persistedScan
    }

    suspend fun findScans(searchParams: FindScansQuery): List<Scan> {
        return scansRepository.findByStatusIn(searchParams.status)
    }

    private fun generateTaskId(): UUID = UUID.randomUUID()

}
