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
import legal.prameya.lexihub.plugins.configureSwaggerUI

fun main(args: Array<String>): Unit = EngineMain.main(args)

const val REQUEST_TIMEOUT = 300000L
const val CONNECT_TIMEOUT = 300000L
const val SOCKET_TIMEOUT = 300000L

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
    configureSwaggerUI()
}

val client =
    HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
        install(HttpTimeout) {
            requestTimeoutMillis = REQUEST_TIMEOUT // 5 minutes
            connectTimeoutMillis = CONNECT_TIMEOUT // 5 minutes
            socketTimeoutMillis = SOCKET_TIMEOUT // 5 minutes
        }
    }

val externalService = ExternalService(client)
