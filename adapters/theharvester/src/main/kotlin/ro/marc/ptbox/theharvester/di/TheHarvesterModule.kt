package ro.marc.ptbox.theharvester.di

import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ro.marc.ptbox.shared.domain.model.Scan
import ro.marc.ptbox.shared.domain.ports.CompletedScansRepository
import ro.marc.ptbox.shared.domain.ports.ScannerPort
import ro.marc.ptbox.theharvester.HarvesterOutputParser
import ro.marc.ptbox.theharvester.HarvesterOutputParserImpl
import ro.marc.ptbox.theharvester.TheHarvesterAdapterImpl
import ro.marc.ptbox.theharvester.TheHarvesterResultCallback

fun getTheHarvesterModule() = module {
    factory<HarvesterOutputParser> {
        HarvesterOutputParserImpl(
            it.get<Scan>(),
            2,
        )
    }
    factory<TheHarvesterResultCallback> {
        TheHarvesterResultCallback(
            get<HarvesterOutputParser> {
                parametersOf(
                    it.get<Scan>(),
                )
            },
            get<CompletedScansRepository>(),
        )
    }
    single<ScannerPort>(named(Scan.Type.THE_HARVESTER)) {
        TheHarvesterAdapterImpl()
    }
}
