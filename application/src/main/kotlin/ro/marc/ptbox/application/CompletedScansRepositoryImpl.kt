package ro.marc.ptbox.application

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import ro.marc.ptbox.shared.domain.CompletedScansRepository
import ro.marc.ptbox.shared.domain.Scan

class CompletedScansRepositoryImpl: CompletedScansRepository {

    private val _scans = MutableSharedFlow<Scan>(extraBufferCapacity = 1)
    override val scans: Flow<Scan>
        get() = _scans

    override fun addScan(scan: Scan) {
        _scans.tryEmit(scan)
    }

}