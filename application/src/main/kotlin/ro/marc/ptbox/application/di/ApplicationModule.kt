package ro.marc.ptbox.application.di

import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import ro.marc.ptbox.application.CompletedScansRepositoryImpl
import ro.marc.ptbox.application.ScanningService
import ro.marc.ptbox.db.di.getDatabaseModule
import ro.marc.ptbox.shared.domain.ScanAdapter
import ro.marc.ptbox.shared.domain.CompletedScansRepository
import ro.marc.ptbox.shared.domain.ScansRepository
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
                    get<ScanAdapter>(),
                    get<CompletedScansRepository>(),
                    get<ScansRepository>(),
                )
            }
        }
    ))
}
