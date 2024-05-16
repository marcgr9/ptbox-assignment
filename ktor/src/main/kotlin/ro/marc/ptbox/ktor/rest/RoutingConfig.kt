package ro.marc.ptbox.ktor.rest

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

class RoutingConfig {

    fun Application.configureRoutes() {
        routing {
            get("") {
                call.respondText("Hello PtBox!")
            }
        }
    }

}
