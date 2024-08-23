package legal.prameya.lexihub

import io.ktor.client.*
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.netty.*
import legal.prameya.lexihub.externalservices.ExternalService
import legal.prameya.lexihub.plugins.*

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
