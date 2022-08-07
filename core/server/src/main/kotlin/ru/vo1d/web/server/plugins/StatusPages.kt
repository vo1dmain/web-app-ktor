package ru.vo1d.web.server.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.request.ContentTransformationException
import ru.vo1d.web.server.errors.UnprocessableEntityException
import ru.vo1d.web.server.extensions.respondError

fun Application.statusPages() {
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

        exception<NotFoundException> { call, cause ->
            call.respondError(HttpStatusCode.NotFound, cause)
        }
    }
}