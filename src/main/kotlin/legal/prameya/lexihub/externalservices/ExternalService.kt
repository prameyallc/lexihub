package legal.prameya.lexihub.externalservices

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.Json
import legal.prameya.lexihub.models.ApiRequest
import legal.prameya.lexihub.models.ApiResponse

class ExternalService(
    private val client: HttpClient,
) {
    suspend fun fetchOllamaData(): ApiResponse.OllamaResponse = client.get("https://api.ollama.com/path") { }.body()

    private val json = Json { ignoreUnknownKeys = true }

    suspend fun fetchOllamaResponse(
        model: String,
        prompt: String,
        stream: Boolean,
    ): ApiResponse.OllamaResponse {
        val response: HttpResponse =
            client.post("http://localhost:11434/api/generate") {
                contentType(ContentType.Application.Json)
                setBody(ApiRequest(model, prompt, stream))
            }

        val responseBody = response.bodyAsText()
        return json.decodeFromString<ApiResponse.OllamaResponse>(responseBody)
    }

    suspend fun fetchCourtListenerData(): ApiResponse.CourtListenerResponse =
        client.get("https://www.courtlistener.com/api/endpoint").body()

    suspend fun fetchRegulationsGovData(): ApiResponse.RegulationsGovResponse =
        client.get("https://www.regulations.gov/api/endpoint").body()

    suspend fun fetchCongressGovData(): ApiResponse.CongressGovResponse = client.get("https://www.congress.gov/api/endpoint").body()

    suspend fun fetchAgencyData(): ApiResponse.AgencyApiResponse =
        client
            .get("https://api.agency.gov/endpoint") {
                // Add any necessary headers or parameters here
                header("Authorization", "Bearer your_access_token")
            }.body()
}
