package ru.vo1d.web.app.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.request.ContentTransformationException
import io.ktor.server.response.*
import ru.vo1d.web.app.errors.UnprocessableEntityException

fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<MissingRequestParameterException> { call, cause ->
            call.respondText(
                "400: Bad request. Missing parameter ${cause.parameterName}",
                status = HttpStatusCode.BadRequest
            )
        }

        exception<UnprocessableEntityException> { call, cause ->
            call.respondText(
                "422: Unprocessable entity. Cause: ${cause.message}",
                status = HttpStatusCode.UnprocessableEntity
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