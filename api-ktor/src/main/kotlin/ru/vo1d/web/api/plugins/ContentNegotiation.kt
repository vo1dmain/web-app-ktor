package ru.vo1d.web.api.plugins

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.serialization.json.Json
import ru.vo1d.web.domain.daybook.timetable.session.sessionsModule

fun Application.contentNegotiation() {
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
            prettyPrint = true
            serializersModule = sessionsModule
        })
    }
}
