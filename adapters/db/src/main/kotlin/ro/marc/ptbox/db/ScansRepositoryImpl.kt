package ro.marc.ptbox.db

import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import ro.marc.ptbox.db.exposed.ScanEntity
import ro.marc.ptbox.shared.domain.Scan
import ro.marc.ptbox.shared.domain.ScansRepository

class ScansRepositoryImpl: ScansRepository {

    override suspend fun create(scan: Scan): Scan = query {
        val entity = ScanEntity.new(scan.id) {
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

    private fun ScanEntity.toDomain() = Scan(this.id.value, this.website, this.status, this.results, this.createdAt)

    private suspend fun <T> query(
        transaction: Transaction? = null,
        block: suspend Transaction.() -> T
    ): T = newSuspendedTransaction { block() }

}
