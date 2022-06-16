package ru.penzgtu.web.app

import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*

fun ApplicationTestBuilder.jsonClient() = createClient {
    install(ContentNegotiation) {
        json()
    }
}