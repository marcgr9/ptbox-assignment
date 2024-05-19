package ro.marc.ptbox.theharvester

import ro.marc.ptbox.shared.domain.model.Scan

interface HarvesterOutputParser {

    fun parseOutput(output: String): Scan

}
