package ru.vo1d.web.api

import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.testing.*
import ru.vo1d.web.api.plugins.contentNegotiation
import ru.vo1d.web.api.plugins.resources
import ru.vo1d.web.api.plugins.statusPages

fun ApplicationTestBuilder.jsonClient() = createClient {
    install(ContentNegotiation) {
        json()
    }
    install(Logging)
}

/**
 * Installs the plugins routing depends on; the rest of the production module list is left out.
 */
fun Application.testPlugins() {
    contentNegotiation()
    resources()
    statusPages()
}
