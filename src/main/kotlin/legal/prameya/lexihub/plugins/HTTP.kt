package legal.prameya.lexihub.plugins

import io.ktor.server.application.*
import io.ktor.server.plugins.defaultheaders.*
import io.ktor.server.plugins.hsts.*
import kotlin.time.Duration

fun Application.configureHTTP() {

    install(DefaultHeaders) {
        header("X-Engine", "Ktor")
    }
    install(HSTS) {
        includeSubDomains = true
        maxAgeInSeconds = 86400
    }
}
