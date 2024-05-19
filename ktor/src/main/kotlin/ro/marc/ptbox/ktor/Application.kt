package ro.marc.ptbox.ktor

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.getKoin
import ro.marc.ptbox.application.di.getApplicationModule
import ro.marc.ptbox.ktor.rest.CorsConfig
import ro.marc.ptbox.ktor.rest.RoutingConfig
import ro.marc.ptbox.ktor.rest.StatusPageConfig
import ro.marc.ptbox.ktor.rest.di.getRestModule
import ro.marc.ptbox.shared.di.getSharedModule
import java.time.Duration

fun main(args: Array<String>) {
    initKoin(args)
    startServer(args)
}

private fun initKoin(args: Array<String>) {
    startKoin {
        modules(
            getSharedModule(commandLineEnvironment(args).config.toMap()),
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
    install(WebSockets) {
        pingPeriod = Duration.ofSeconds(15)
        timeout = Duration.ofSeconds(15)
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }

    with(getKoin().get<RoutingConfig>()) {
        configureRoutes()
    }
}
