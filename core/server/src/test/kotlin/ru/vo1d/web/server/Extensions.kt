package ru.vo1d.web.server

import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import kotlin.math.min

fun ApplicationTestBuilder.jsonClient() = createClient {
    install(ContentNegotiation) {
        json()
    }
    install(Logging)
}

fun <E> List<E>.clampedSubList(from: Int, limit: Int): List<E> {
    val start = min(size - 1, from)
    val end = min(size, from + limit)
    return subList(start, end)
}