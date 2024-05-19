package ro.marc.ptbox.shared.domain.ports

import kotlinx.coroutines.flow.Flow
import ro.marc.ptbox.shared.domain.model.Scan

interface CompletedScansRepository {

    val scans: Flow<Scan>

    fun addScan(scan: Scan)

}
