package ro.marc.ptbox.ktor.rest

import io.ktor.http.*
import io.ktor.server.plugins.cors.*

class CorsConfig {

    fun CORSConfig.configureCors() {
        allowMethod(HttpMethod.Get)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Patch)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Head)

        allowHeader(HttpHeaders.AccessControlAllowOrigin)
        allowHeader(HttpHeaders.ContentType)

        anyHost()
    }

}
