package legal.prameya.lexihub.plugins

import io.github.smiley4.ktorswaggerui.SwaggerUI
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.webjars.*
import legal.prameya.lexihub.externalservices.ExternalService

fun Application.configureRouting() {
    install(Webjars) {
        path = "/webjars" //defaults to /webjars
    }
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
    routing {
        // Route for Ollama API
        get("/api/ollama") {
            try {
                val data = ExternalService().fetchOllamaData()
                call.respond(HttpStatusCode.OK, data)
            } catch (e: Exception) {
                call.respondText("Failed to fetch Ollama data: ${e.message}", status = HttpStatusCode.InternalServerError)
            }
        }

        // Route for CourtListener API
        get("/api/courtlistener") {
            try {
                val data = ExternalService().fetchCourtListenerData()
                call.respond(HttpStatusCode.OK, data)
            } catch (e: Exception) {
                call.respondText("Failed to fetch CourtListener data: ${e.message}", status = HttpStatusCode.InternalServerError)
            }
        }

        // Route for Regulations.gov API
        get("/api/regulationsgov") {
            try {
                val data = ExternalService().fetchRegulationsGovData()
                call.respond(HttpStatusCode.OK, data)
            } catch (e: Exception) {
                call.respondText("Failed to fetch Regulations.gov data: ${e.message}", status = HttpStatusCode.InternalServerError)
            }
        }

        // Route for Congress.gov API
        get("/api/congressgov") {
            try {
                val data = ExternalService().fetchCongressGovData()
                call.respond(HttpStatusCode.OK, data)
            } catch (e: Exception) {
                call.respondText("Failed to fetch Congress.gov data: ${e.message}", status = HttpStatusCode.InternalServerError)
            }
        }

        // Placeholder for additional federal and state agency APIs
        // Example route for another API
        get("/api/agency") {
            try {
                val data = ExternalService().fetchAgencyData() // Implement this method in ExternalService
                call.respond(HttpStatusCode.OK, data)
            } catch (e: Exception) {
                call.respondText("Failed to fetch agency data: ${e.message}", status = HttpStatusCode.InternalServerError)
            }
        }
    }
}
