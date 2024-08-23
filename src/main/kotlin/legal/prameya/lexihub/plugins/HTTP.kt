package legal.prameya.lexihub.plugins

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.defaultheaders.DefaultHeaders
import io.ktor.server.plugins.hsts.HSTS

fun Application.configureHTTP() {
    install(DefaultHeaders) {
        header("X-Engine", "Ktor")
    }
    install(HSTS) {
        includeSubDomains = true
        maxAgeInSeconds = 86400
    }
}
