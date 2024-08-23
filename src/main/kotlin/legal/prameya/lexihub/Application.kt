package legal.prameya.lexihub

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.netty.EngineMain
import legal.prameya.lexihub.externalservices.ExternalService
import legal.prameya.lexihub.plugins.configureCORS
import legal.prameya.lexihub.plugins.configureCaching
import legal.prameya.lexihub.plugins.configureCompression
import legal.prameya.lexihub.plugins.configureHTTP
import legal.prameya.lexihub.plugins.configureLogging
import legal.prameya.lexihub.plugins.configureMonitoring
import legal.prameya.lexihub.plugins.configureRateLimiting
import legal.prameya.lexihub.plugins.configureRouting
import legal.prameya.lexihub.plugins.configureSerialization

fun main(args: Array<String>): Unit = EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
fun Application.module() {
    configureHTTP()
    configureCORS()
    configureCompression()
    configureMonitoring()
    configureSerialization()
    configureRouting(externalService)
    configureLogging()
    configureRateLimiting()
    configureCaching()
}

val client =
    HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 300000 // 5 minutes
            connectTimeoutMillis = 300000 // 5 minutes
            socketTimeoutMillis = 300000 // 5 minutes
        }
    }

val externalService = ExternalService(client)
