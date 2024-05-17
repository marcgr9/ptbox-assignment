package ro.marc.ptbox.shared.domain

import java.util.*

data class Scan(
    val id: UUID,
    val type: Type,
    val website: String,

    val results: Any?,
) {

    enum class Type {
        AMASS,
        THE_HARVESTER,
    }

}
