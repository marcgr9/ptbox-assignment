package ro.marc.ptbox.ktor.rest

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ro.marc.ptbox.application.ScanningService
import ro.marc.ptbox.shared.domain.model.Scan
import ro.marc.ptbox.shared.dto.FindScansQuery
import ro.marc.ptbox.shared.dto.ScansQueryDTO
import ro.marc.ptbox.shared.dto.StartScanDTO

class RoutingConfig(
    private val service: ScanningService,
) {

    fun Application.configureRoutes() {
        routing {
            post("/scans") {
                val dto = call.receive<StartScanDTO>()

                call.respond<Scan>(
                    service.scanDomain(dto.website, dto.scanType)
                )
            }
            get("/scans") {
                val searchParams = call.request.queryParameters.toGetScansQueryParameters()

                call.respond(
                    ScansQueryDTO(service.findScans(searchParams))
                )
            }

            webSocket("/") {
                service.scanCompletedEvent.collect { c ->
                    send(Frame.Text(Json.encodeToString(c)))
                }
            }
        }
    }

    private fun Parameters.toGetScansQueryParameters() = FindScansQuery(
        this.getAll("status")?.map { Scan.Status.valueOf(it) } ?: listOf()
    )

}
