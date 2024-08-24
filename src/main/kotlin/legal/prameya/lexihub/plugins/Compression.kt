package legal.prameya.lexihub.plugins

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.compression.Compression
import io.ktor.server.plugins.compression.deflate
import io.ktor.server.plugins.compression.gzip
import io.ktor.server.plugins.compression.minimumSize

const val DEFLATE_MINIMUM_SIZE = 1024L
const val GZIP_PRIORITY = 1.0
const val DEFLATE_PRIORITY = 10.0

fun Application.configureCompression() {
    install(Compression) {
        gzip {
            priority = GZIP_PRIORITY
        }
        deflate {
            priority = DEFLATE_PRIORITY
            minimumSize(DEFLATE_MINIMUM_SIZE)
        }
    }
}
