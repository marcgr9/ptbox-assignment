package ro.marc.ptbox.shared.dto

import ro.marc.ptbox.shared.domain.model.Scan

data class FindScansQuery(
    val status: List<Scan.Status>,
)
