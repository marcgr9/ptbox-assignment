package ro.marc.ptbox.db

import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import ro.marc.ptbox.db.exposed.ScanEntity
import ro.marc.ptbox.db.exposed.ScansTable
import ro.marc.ptbox.shared.domain.model.Scan
import ro.marc.ptbox.shared.domain.ports.ScansRepository

class ScansRepositoryImpl: ScansRepository {

    override suspend fun create(scan: Scan): Scan = query {
        val entity = ScanEntity.new(scan.id) {
            type = scan.type
            website = scan.website
            results = scan.results
            status = scan.status
        }

        entity.toDomain()
    }

    override suspend fun update(scan: Scan): Scan = query {
        val entity = ScanEntity[scan.id].apply {
            status = scan.status
            results = scan.results
        }

        entity.toDomain()
    }

    override suspend fun findByStatusIn(status: List<Scan.Status>): List<Scan> = query {
        ScanEntity
            .let { query ->
                if (status.isNotEmpty()) {
                    query.find { ScansTable.status inList status }
                } else {
                    query.all()
                }
            }
            .orderBy(ScansTable.createdAt to SortOrder.DESC)
            .map { it.toDomain() }
    }

    private fun ScanEntity.toDomain() = Scan(this.id.value, this.type, this.website, this.status, this.results, this.createdAt)

    private suspend fun <T> query(
        transaction: Transaction? = null,
        block: suspend Transaction.() -> T
    ): T = newSuspendedTransaction { block() }

}
