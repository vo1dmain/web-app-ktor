package ru.vo1d.web.app.plugins

import io.ktor.server.application.*
import io.ktor.server.routing.*
import ru.vo1d.web.app.routing.daybookRouting
import ru.vo1d.web.app.routing.newsRouting
import ru.vo1d.web.app.routing.qnaRouting

fun Application.configureRouting() {
    install(IgnoreTrailingSlash)
    routing {
        route("/api/v1") {
            newsRouting()
            qnaRouting()
            daybookRouting()
        }
    }
}
