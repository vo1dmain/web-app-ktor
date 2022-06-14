package ru.penzgtu.web.app.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureStatusPages() {
    install(StatusPages) {
        status(HttpStatusCode.BadRequest) { call, status ->
            call.respondText("400: Bad request", status = status)
        }
    }
}