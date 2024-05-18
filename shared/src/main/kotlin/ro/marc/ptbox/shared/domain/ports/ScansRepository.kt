package ro.marc.ptbox.shared.domain.ports

import ro.marc.ptbox.shared.domain.model.Scan

interface ScansRepository {

    suspend fun create(scan: Scan): Scan

    suspend fun update(scan: Scan): Scan

    suspend fun findByStatusIn(status: List<Scan.Status>): List<Scan>

}
