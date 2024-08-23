val kotlinVersion: String by project
val logbackVersion: String by project
val kotlinxHtmlVersion: String by project

plugins {
    kotlin("jvm") version "2.0.10"
    id("io.ktor.plugin") version "2.3.12"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.10"
    id("org.owasp.dependencycheck") version "10.0.3"
    id("org.jlleitschuh.gradle.ktlint") version "12.1.1"
}

group = "legal.prameya"
version = "0.0.1"

application {
    mainClass.set("legal.prameya.lexihub.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

tasks.withType<JavaExec> {
    args = listOf("-config=src/main/resources/application.conf")
}

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/kotlin-js-wrappers") }
}

dependencies {
    // Ktor Server
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-call-logging")
    implementation("com.apurebase:kgraphql-ktor:0.19.0")
    implementation("io.ktor:ktor-server-webjars-jvm")
    implementation("io.ktor:ktor-server-compression-jvm")
    implementation("io.ktor:ktor-server-cors-jvm")
    implementation("io.ktor:ktor-server-default-headers-jvm")
    implementation("io.ktor:ktor-server-hsts-jvm")
    implementation("io.ktor:ktor-server-rate-limit")
    implementation("io.ktor:ktor-server-openapi")
    implementation("io.ktor:ktor-server-auth")
    implementation("io.ktor:ktor-server-metrics-jvm")
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("io.ktor:ktor-server-caching-headers")

    // Ktor Client
    implementation("io.ktor:ktor-client-core")
    implementation("io.ktor:ktor-client-cio")
    implementation("io.ktor:ktor-client-content-negotiation")
    implementation("io.ktor:ktor-client-auth")

    // Swagger UI
    implementation("io.github.smiley4:ktor-swagger-ui:2.9.0")

    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")
    implementation("io.ktor:ktor-server-html-builder-jvm")
    implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:$kotlinxHtmlVersion")
    implementation("io.ktor:ktor-server-netty-jvm")

    // Logging
    implementation("ch.qos.logback:logback-classic:$logbackVersion")

    // Dependency Check
    implementation("org.owasp:dependency-check-gradle:10.0.3")

    // Testing
    testImplementation("io.ktor:ktor-server-test-host-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")
}

dependencyCheck {
    format =
        org.owasp.dependencycheck.reporting.ReportGenerator.Format.ALL
            .toString()

    suppressionFile = "dependency-check-suppressions.xml"
    failBuildOnCVSS = 7.0f
    analyzers(
        closureOf<org.owasp.dependencycheck.gradle.extension.AnalyzerExtension> {
            assemblyEnabled = false
        },
    )
}

ktlint {
    verbose = true
    outputToConsole = true
}

ktor {
    docker {
        jreVersion.set(JavaVersion.VERSION_21)
    }
}
