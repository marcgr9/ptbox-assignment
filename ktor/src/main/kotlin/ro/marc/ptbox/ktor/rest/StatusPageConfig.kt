package ro.marc.ptbox.ktor.rest

import io.ktor.http.*
import io.ktor.server.plugins.statuspages.StatusPagesConfig
import io.ktor.server.response.*

class StatusPageConfig {

    fun StatusPagesConfig.configureStatusPages() {
        exception<Exception> { call, ex ->
            ex.printStackTrace()
            call.respondText("error", status = HttpStatusCode.BadRequest)
        }
    }

}
