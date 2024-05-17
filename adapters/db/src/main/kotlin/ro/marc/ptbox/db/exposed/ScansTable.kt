package ro.marc.ptbox.db.exposed

import kotlinx.datetime.toKotlinLocalDateTime
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.json.json
import org.jetbrains.exposed.sql.kotlin.datetime.datetime
import ro.marc.ptbox.db.exposed.util.PGEnum
import ro.marc.ptbox.shared.domain.Scan
import java.time.LocalDateTime

object ScansTable: UUIDTable(name = "scans") {

    private const val PG_STATUS_ENUM_DEF = "StatusEnum"

    var website = varchar("website", 50)
    var status = customEnumeration(
        "status",
        PG_STATUS_ENUM_DEF,
        { value ->
            Scan.Status.valueOf(value as String)
        },
        {
            PGEnum(PG_STATUS_ENUM_DEF, it)
        }
    )
    var results = json<List<String>>("results", Json { prettyPrint = true })
    var createdAt = datetime("created_at").clientDefault { LocalDateTime.now().toKotlinLocalDateTime() }


    val pgCreateEnumSql = "CREATE TYPE $PG_STATUS_ENUM_DEF AS ENUM (${Scan.Status.entries.joinToString(separator = ",") { "'${it.name.uppercase()}'" }});"

}