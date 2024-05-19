package ro.marc.ptbox.theharvester.di

import org.koin.core.qualifier.named
import org.koin.dsl.module
import ro.marc.ptbox.shared.domain.model.Scan
import ro.marc.ptbox.shared.domain.ports.CompletedScansRepository
import ro.marc.ptbox.shared.domain.ports.ScannerPort
import ro.marc.ptbox.theharvester.AmassAdapterImpl
import ro.marc.ptbox.theharvester.AmassResultCallback

fun getAmassModule() = module {
    factory<AmassResultCallback> {
        AmassResultCallback(
            get<CompletedScansRepository>(),
            it.get<Scan>(),
        )
    }
    single<ScannerPort>(named(Scan.Type.AMASS)) {
        AmassAdapterImpl()
    }
}
