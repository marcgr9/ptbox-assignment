package ro.marc.ptbox.application.di

import kotlinx.coroutines.flow.MutableSharedFlow
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import ro.marc.ptbox.application.CompletedScansRepositoryImpl
import ro.marc.ptbox.application.ScanningService
import ro.marc.ptbox.shared.domain.AmassAdapter
import ro.marc.ptbox.shared.domain.CompletedScansRepository
import ro.marc.ptbox.theharvester.di.getAmassModule

fun getApplicationModule() = module {
    loadKoinModules(listOf(
        module {
            single<CompletedScansRepository> {
                CompletedScansRepositoryImpl()
            }
        },

        getAmassModule(),

        module {
            single {
                ScanningService(
                    get<AmassAdapter>(),
                    get<CompletedScansRepository>(),
                )
            }
        }
    ))
}
