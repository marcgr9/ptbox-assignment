package ro.marc.ptbox.ktor.rest.di

import org.koin.dsl.module
import ro.marc.ptbox.application.ScanningService
import ro.marc.ptbox.ktor.rest.CorsConfig
import ro.marc.ptbox.ktor.rest.RoutingConfig
import ro.marc.ptbox.ktor.rest.StatusPageConfig

internal fun getRestModule() = module {
    single {
        CorsConfig()
    }
    single {
        RoutingConfig(
            get<ScanningService>(),
        )
    }
    single {
        StatusPageConfig()
    }
}
