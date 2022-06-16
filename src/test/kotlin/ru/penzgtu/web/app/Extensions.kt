package ru.penzgtu.web.app

import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import kotlin.math.min

fun ApplicationTestBuilder.jsonClient() = createClient {
    install(ContentNegotiation) {
        json()
    }
}

fun <E> List<E>.clampedSubList(from: Int, limit: Int): List<E> {
    val start = min(size - 1, from)
    val end = min(size - 1, from + limit)
    return subList(start, end)
}