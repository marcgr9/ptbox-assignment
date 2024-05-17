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
                val taskId = service.runAmass(website!!)

                call.respondText("Task $taskId processing")
            }

            webSocket("") {
                service.scanCompletedEvent.collect { c ->
                    println(c)
                    send(Frame.Text("Scan #${c.id}: ${c.status} ${c.website} = [${c.results}]"))
                }
            }
        }
    }

}
