package ro.marc.ptbox.shared.dto

import ro.marc.ptbox.shared.domain.model.Scan
import kotlinx.serialization.Serializable

@Serializable
data class ScansQueryDTO(
    val scans: List<Scan>
)
