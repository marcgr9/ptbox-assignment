package ro.marc.ptbox.shared.dto

import kotlinx.serialization.Serializable
import ro.marc.ptbox.shared.domain.model.Scan

@Serializable
data class StartScanDTO(
    val website: String,
    val scanType: Scan.Type,
)
