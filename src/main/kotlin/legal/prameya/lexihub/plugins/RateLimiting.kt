package legal.prameya.lexihub.plugins

import io.ktor.server.application.*
import io.ktor.server.plugins.ratelimit.*
import kotlin.time.Duration.Companion.seconds

fun Application.configureRateLimiting() {
    install(RateLimit) {
        global {
            rateLimiter(limit = 100, refillPeriod = 60.seconds)
        }
    }
}