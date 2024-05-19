package ro.marc.ptbox.shared.domain.ports

import ro.marc.ptbox.shared.domain.model.Scan


interface ScannerPort {

    fun processWebsite(scan: Scan)

}
