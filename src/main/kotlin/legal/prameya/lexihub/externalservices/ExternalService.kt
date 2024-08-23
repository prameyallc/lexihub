package legal.prameya.lexihub.externalservices

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import legal.prameya.lexihub.models.ApiResponses
import legal.prameya.lexihub.models.GenerateRequest

class ExternalService(
    private val client: HttpClient,
) {
    suspend fun fetchOllamaData(): ApiResponses.OllamaResponse = client.get("https://api.ollama.com/path") { }.body()

    private val json = Json { ignoreUnknownKeys = true }

    suspend fun fetchOllamaResponse(
        model: String,
        prompt: String,
        stream: Boolean,
    ): ApiResponses.OllamaResponse {
        val response: HttpResponse =
            client.post("http://localhost:11434/api/generate") {
                contentType(ContentType.Application.Json)
                setBody(GenerateRequest(model, prompt, stream))
            }

        val responseBody = response.bodyAsText()
        return json.decodeFromString<ApiResponses.OllamaResponse>(responseBody)
    }

    suspend fun fetchCourtListenerData(): ApiResponses.CourtListenerResponse =
        client.get("https://www.courtlistener.com/api/endpoint").body()

    suspend fun fetchRegulationsGovData(): ApiResponses.RegulationsGovResponse =
        client.get("https://www.regulations.gov/api/endpoint").body()

    suspend fun fetchCongressGovData(): ApiResponses.CongressGovResponse = client.get("https://www.congress.gov/api/endpoint").body()

    suspend fun fetchAgencyData(): ApiResponses.AgencyApiResponse =
        client
            .get("https://api.agency.gov/endpoint") {
                // Add any necessary headers or parameters here
                header("Authorization", "Bearer your_access_token")
            }.body()
}
