package ro.marc.ptbox.db.exposed

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class ScanEntity(id: EntityID<UUID>): UUIDEntity(id) {

    var type by ScansTable.type
    var website by ScansTable.website
    var status by ScansTable.status
    var results by ScansTable.results
    var createdAt by ScansTable.createdAt
    var completedAt by ScansTable.completedAt

    companion object : UUIDEntityClass<ScanEntity>(ScansTable)
}
