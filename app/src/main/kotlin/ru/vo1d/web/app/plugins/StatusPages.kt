package ru.vo1d.web.app.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.request.ContentTransformationException
import ru.vo1d.web.app.errors.UnprocessableEntityException
import ru.vo1d.web.app.extensions.respondError

fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<BadRequestException> { call, cause ->
            call.respondError(HttpStatusCode.BadRequest, cause)
        }

        exception<UnprocessableEntityException> { call, cause ->
            call.respondError(HttpStatusCode.UnprocessableEntity, cause)
        }

        exception<ContentTransformationException> { call, cause ->
            val code = when (cause) {
                is UnsupportedMediaTypeException -> HttpStatusCode.UnsupportedMediaType
                else -> HttpStatusCode.BadRequest
            }
            call.respondError(code, cause)
        }
    }
}