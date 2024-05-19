package ro.marc.ptbox.shared.domain.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import ro.marc.ptbox.shared.util.UUIDSerializer
import java.util.*

@Serializable
data class Scan(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val type: Type,
    val website: String,
    val status: Status,
    val results: Results,
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

    @Serializable
    data class Results(
        val whoIs: List<String>,
        val ips: List<String>,
        val emails: List<String>,
        val subdomains: List<String>,
    ) {

        companion object {
            fun empty() = Results(emptyList(), emptyList(), emptyList(), emptyList())
        }

    }

}
