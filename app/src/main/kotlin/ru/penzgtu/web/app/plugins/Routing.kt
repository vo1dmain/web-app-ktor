package ru.penzgtu.web.app.plugins

import io.ktor.server.application.*
import io.ktor.server.routing.*
import ru.penzgtu.web.app.routing.newsRouting
import ru.penzgtu.web.app.routing.qnaRouting
import ru.penzgtu.web.app.routing.timetablesRouting

fun Application.configureRouting() {
    routing {
        route("/api/v1") {
            newsRouting()
            qnaRouting()
            timetablesRouting()
        }
    }
}
