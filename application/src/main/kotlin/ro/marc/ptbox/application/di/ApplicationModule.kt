package ro.marc.ptbox.application.di

import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ro.marc.ptbox.application.CompletedScansRepositoryImpl
import ro.marc.ptbox.application.ScanningService
import ro.marc.ptbox.db.di.getDatabaseModule
import ro.marc.ptbox.shared.domain.model.Scan
import ro.marc.ptbox.shared.domain.ports.ScannerPort
import ro.marc.ptbox.shared.domain.ports.CompletedScansRepository
import ro.marc.ptbox.shared.domain.ports.ScansRepository
import ro.marc.ptbox.theharvester.di.getAmassModule
import ro.marc.ptbox.theharvester.di.getTheHarvesterModule

fun getApplicationModule() = module {
    loadKoinModules(listOf(
        module {
            single<CompletedScansRepository> {
                CompletedScansRepositoryImpl()
            }
        },

        getDatabaseModule(),
        getAmassModule(),
        getTheHarvesterModule(),

        module {
            single {
                ScanningService(
                    get<ScannerPort>(named(Scan.Type.AMASS)),
                    get<ScannerPort>(named(Scan.Type.THE_HARVESTER)),
                    get<CompletedScansRepository>(),
                    get<ScansRepository>(),
                )
            }
        }
    ))
}
