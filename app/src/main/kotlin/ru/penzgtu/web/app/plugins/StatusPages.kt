package ru.penzgtu.web.app.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.request.ContentTransformationException
import io.ktor.server.response.*

fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<MissingRequestParameterException> { call, cause ->
            call.respondText(
                "400: Bad request. Missing parameter ${cause.parameterName}",
                status = HttpStatusCode.BadRequest
            )
        }

        exception<ContentTransformationException> { call, _ ->
            call.respondText(
                "400: Bad request. Unable to read data.",
                status = HttpStatusCode.BadRequest
            )
        }
    }
}