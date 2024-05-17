package ro.marc.ptbox.shared.domain

interface ScansRepository {

    suspend fun create(scan: Scan): Scan

    suspend fun update(scan: Scan): Scan

}
