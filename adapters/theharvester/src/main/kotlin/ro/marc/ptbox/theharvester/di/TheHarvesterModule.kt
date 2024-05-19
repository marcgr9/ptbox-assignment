package ro.marc.ptbox.theharvester.di

import org.koin.core.qualifier.named
import org.koin.dsl.module
import ro.marc.ptbox.shared.domain.model.Scan
import ro.marc.ptbox.shared.domain.ports.ScannerPort
import ro.marc.ptbox.theharvester.TheHarvesterAdapterImpl
import ro.marc.ptbox.theharvester.TheHarvesterResultCallback

fun getTheHarvesterModule() = module {
    factory<TheHarvesterResultCallback> {
        TheHarvesterResultCallback()
    }
    single<ScannerPort>(named(Scan.Type.THE_HARVESTER)) {
        TheHarvesterAdapterImpl()
    }
}
