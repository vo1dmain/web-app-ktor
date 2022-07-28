package ru.vo1d.web.app.plugins

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.serialization.json.Json
import ru.vo1d.web.entities.daybook.timetable.dtoModule

fun Application.contentNegotiation() {
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
            prettyPrint = true
            serializersModule = dtoModule
        })
    }
}
