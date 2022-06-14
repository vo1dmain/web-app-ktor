package ru.penzgtu.web.app.plugins

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.penzgtu.web.app.routing.NewsRouting.newsRouting
import ru.penzgtu.web.app.routing.QnaRouting.qnaRouting

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        route("/api") {
            newsRouting()
            qnaRouting()
        }
    }
}
