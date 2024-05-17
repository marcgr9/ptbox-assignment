package ro.marc.ptbox.ktor.rest

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import ro.marc.ptbox.application.ScanningService

class RoutingConfig(
    private val service: ScanningService,
) {

    fun Application.configureRoutes() {
        routing {
            get("/{website}") {
                val website = call.parameters["website"]
                println(website)
                val params = call.request.queryParameters
                println(params["tool"])
                service.runAmass(website!!)
                call.respondText("Hello PtBox!")
            }

            webSocket("") {
                service.scansRepository.scans.collect { c ->
                    println(c)
                    send(Frame.Text(c))
                }
            }
        }
    }

}
