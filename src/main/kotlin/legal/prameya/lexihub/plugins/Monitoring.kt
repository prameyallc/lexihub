package legal.prameya.lexihub.plugins

import com.codahale.metrics.Slf4jReporter
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.application.log
import io.ktor.server.metrics.dropwizard.DropwizardMetrics
import java.util.concurrent.TimeUnit

const val POLL_FREQUENCY = 30L

fun Application.configureMonitoring() {
    install(DropwizardMetrics) {
        Slf4jReporter
            .forRegistry(registry)
            .outputTo(this@configureMonitoring.log)
            .convertRatesTo(TimeUnit.SECONDS)
            .convertDurationsTo(TimeUnit.MILLISECONDS)
            .build()
            .start(POLL_FREQUENCY, TimeUnit.SECONDS)
    }
}
