package legal.prameya.lexihub.externalservices

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import legal.prameya.lexihub.models.ApiResponses

class ExternalService {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
        install(Auth) {
            bearer {
                loadTokens {
                    BearerTokens("your_access_token", "your_refresh_token")
                }
            }
        }
    }

    suspend fun fetchOllamaData(): ApiResponses.OllamaResponse {
        return client.get("https://api.ollama.com/path") { }.body()
    }

    suspend fun fetchCourtListenerData(): ApiResponses.CourtListenerResponse {
        return client.get("https://www.courtlistener.com/api/endpoint").body()
    }

    suspend fun fetchRegulationsGovData(): ApiResponses.RegulationsGovResponse {
        return client.get("https://www.regulations.gov/api/endpoint").body()
    }

    suspend fun fetchCongressGovData(): ApiResponses.CongressGovResponse {
        return client.get("https://www.congress.gov/api/endpoint").body()
    }

    suspend fun fetchAgencyData(): ApiResponses.AgencyApiResponse {
        return client.get("https://api.agency.gov/endpoint") {
            // Add any necessary headers or parameters here
            header("Authorization", "Bearer your_access_token")
        }.body()
    }
}