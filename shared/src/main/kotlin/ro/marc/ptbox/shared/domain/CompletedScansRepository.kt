package ro.marc.ptbox.shared.domain

import kotlinx.coroutines.flow.Flow

interface CompletedScansRepository {

    val scans: Flow<String>

    fun addScan(scan: String)

}
