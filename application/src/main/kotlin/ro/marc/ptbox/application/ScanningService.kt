package ro.marc.ptbox.application

import ro.marc.ptbox.shared.domain.AmassAdapter
import ro.marc.ptbox.shared.domain.CompletedScansRepository

class ScanningService(
    private val amassAdapter: AmassAdapter,
    val scansRepository: CompletedScansRepository,
) {

    fun runAmass(website: String) {
        amassAdapter.processWebsite(website)
    }

}
