package ro.marc.ptbox.shared.domain

import kotlinx.datetime.LocalDateTime
import java.util.*

data class Scan(
    val id: UUID,
//    val type: Type,
    val website: String,
    val status: Status,
    val results: List<String>,
    val createdAt: LocalDateTime? = null,
) {

    enum class Type {
        AMASS,
        THE_HARVESTER,
    }

    enum class Status {
        COMPLETED,
        FAILED,
        PENDING,
    }

}
