package ro.marc.ptbox.application

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import ro.marc.ptbox.shared.domain.CompletedScansRepository

class CompletedScansRepositoryImpl: CompletedScansRepository {

    private val _scans = MutableSharedFlow<String>(extraBufferCapacity = 1)
    override val scans: Flow<String>
        get() = _scans

    override fun addScan(scan: String) {
        _scans.tryEmit(scan)
    }

}