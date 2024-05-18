package ro.marc.ptbox.application.di

import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import ro.marc.ptbox.application.CompletedScansRepositoryImpl
import ro.marc.ptbox.application.ScanningService
import ro.marc.ptbox.db.di.getDatabaseModule
import ro.marc.ptbox.shared.domain.ports.ScannerPort
import ro.marc.ptbox.shared.domain.ports.CompletedScansRepository
import ro.marc.ptbox.shared.domain.ports.ScansRepository
import ro.marc.ptbox.theharvester.di.getAmassModule

fun getApplicationModule() = module {
    loadKoinModules(listOf(
        module {
            single<CompletedScansRepository> {
                CompletedScansRepositoryImpl()
            }
        },

        getDatabaseModule(),
        getAmassModule(),

        module {
            single {
                ScanningService(
                    get<ScannerPort>(),
                    get<CompletedScansRepository>(),
                    get<ScansRepository>(),
                )
            }
        }
    ))
}
