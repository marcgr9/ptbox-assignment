package ro.marc.ptbox.ktor

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.config.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.routing.*
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent.getKoin
import ro.marc.ptbox.application.di.getApplicationModule
import ro.marc.ptbox.ktor.rest.CorsConfig
import ro.marc.ptbox.ktor.rest.RoutingConfig
import ro.marc.ptbox.ktor.rest.StatusPageConfig
import ro.marc.ptbox.ktor.rest.di.getRestModule
import ro.marc.ptbox.shared.GlobalConfig
import ro.marc.ptbox.shared.di.getSharedModule

fun main(args: Array<String>) {
    initKoin(args)
    startServer(args)
}

private fun initKoin(args: Array<String>) {
    startKoin {
        modules(
            getConfigModule(commandLineEnvironment(args).config),
            getSharedModule(),
            getApplicationModule(),
            getRestModule(),
        )
    }
}

private fun startServer(args: Array<String>) {
    val env = commandLineEnvironment(args)

    val server = embeddedServer(
        Netty,
        environment = env,
    )

    server.application.applyPlugins()
    server.start(wait = true)
}

private fun Application.applyPlugins() {
    install(ContentNegotiation) {
        json()
    }

    install(CORS) {
        with(getKoin().get<CorsConfig>()) {
            configureCors()
        }
    }

    install(StatusPages) {
        with(getKoin().get<StatusPageConfig>()) {
            configureStatusPages()
        }
    }

    install(Routing)
    with(getKoin().get<RoutingConfig>()) {
        configureRoutes()
    }
}

private fun getConfigModule(config: ApplicationConfig) = module(createdAtStart = true) {
    single {
        GlobalConfig(config.toMap())
    }
}
