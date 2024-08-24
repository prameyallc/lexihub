package legal.prameya.lexihub.plugins

import io.github.smiley4.ktorswaggerui.SwaggerUI
import io.ktor.server.application.Application
import io.ktor.server.application.install

fun Application.configureSwaggerUI() {
    install(SwaggerUI) {
        swagger {
            swaggerUrl = "swagger-ui"
            forwardRoot = true
        }
        info {
            title = "LexiHub API"
            version = "latest"
            description = "API for all publicly available sources of law in the United States"
        }
        server {
            url = "http://localhost:8080"
            description = "Development Server"
        }
    }
}
