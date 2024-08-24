package legal.prameya.lexihub.plugins

import io.ktor.http.HttpStatusCode
import io.ktor.serialization.ContentConvertException
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import io.ktor.server.webjars.Webjars
import legal.prameya.lexihub.client
import legal.prameya.lexihub.externalservices.ExternalService
import legal.prameya.lexihub.models.ApiRequest

fun Application.configureRouting(externalService: ExternalService) {
    install(Webjars) {
        path = "/webjars" // defaults to /webjars
    }
    routing {
        // Route for Ollama API
        get("/api/ollama") {
            try {
                val data = ExternalService(client).fetchOllamaData()
                call.respond(HttpStatusCode.OK, data)
            } catch (e: Exception) {
                call.respondText(
                    "Failed to fetch Ollama data: ${e.message}",
                    status = HttpStatusCode.InternalServerError,
                )
            }
        }

        // Route for Ollama Prompt Generation
        post("/ollama/generate") {
            try {
                val request = call.receive<ApiRequest>()
                val ollamaResponse = externalService.fetchOllamaResponse(request.model, request.prompt, request.stream)
                call.respond(ollamaResponse)
            } catch (e: ContentConvertException) {
                call.respond(HttpStatusCode.BadRequest, "Invalid request format: ${e.message}")
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, "Error: ${e.message}")
            }
        }

        // Route for CourtListener API
        get("/api/courtlistener") {
            try {
                val data = ExternalService(client).fetchCourtListenerData()
                call.respond(HttpStatusCode.OK, data)
            } catch (e: Exception) {
                call.respondText(
                    "Failed to fetch CourtListener data: ${e.message}",
                    status = HttpStatusCode.InternalServerError,
                )
            }
        }

        // Route for Regulations.gov API
        get("/api/regulationsgov") {
            try {
                val data = ExternalService(client).fetchRegulationsGovData()
                call.respond(HttpStatusCode.OK, data)
            } catch (e: Exception) {
                call.respondText(
                    "Failed to fetch Regulations.gov data: ${e.message}",
                    status = HttpStatusCode.InternalServerError,
                )
            }
        }

        // Route for Congress.gov API
        get("/api/congressgov") {
            try {
                val data = ExternalService(client).fetchCongressGovData()
                call.respond(HttpStatusCode.OK, data)
            } catch (e: Exception) {
                call.respondText(
                    "Failed to fetch Congress.gov data: ${e.message}",
                    status = HttpStatusCode.InternalServerError,
                )
            }
        }

        // Placeholder for additional federal and state agency APIs
        // Example route for another API
        get("/api/agency") {
            try {
                val data = ExternalService(client).fetchAgencyData() // Implement this method in ExternalService
                call.respond(HttpStatusCode.OK, data)
            } catch (e: Exception) {
                call.respondText(
                    "Failed to fetch agency data: ${e.message}",
                    status = HttpStatusCode.InternalServerError,
                )
            }
        }
    }
}
