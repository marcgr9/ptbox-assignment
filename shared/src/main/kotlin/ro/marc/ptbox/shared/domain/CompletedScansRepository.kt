package ro.marc.ptbox.shared.domain

import kotlinx.coroutines.flow.Flow

interface CompletedScansRepository {

    val scans: Flow<Scan>

    fun addScan(scan: Scan)

}
