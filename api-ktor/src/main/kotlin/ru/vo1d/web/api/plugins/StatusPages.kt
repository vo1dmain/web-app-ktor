package ru.vo1d.web.api.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.request.ContentTransformationException
import io.ktor.server.request.httpMethod
import io.ktor.server.request.uri
import ru.vo1d.web.api.errors.InvalidRequestException
import ru.vo1d.web.api.errors.UnprocessableEntityException
import ru.vo1d.web.api.extensions.respondError

/**
 * Maps exceptions to [ru.vo1d.web.api.errors.ErrorResponse]. Only messages of our own exceptions reach the
 * client; anything else gets a fixed reason, and unexpected errors are logged in full.
 */
internal fun Application.statusPages() {
    install(StatusPages) {
        exception<BadRequestException> { call, cause ->
            // Ktor wraps failed parameter conversion, including our checks in resource `init` blocks
            val reason = cause.findCause<InvalidRequestException>()?.message ?: "Malformed request"
            call.respondError(HttpStatusCode.BadRequest, reason)
        }

        exception<UnprocessableEntityException> { call, cause ->
            call.respondError(HttpStatusCode.UnprocessableEntity, cause.message.orEmpty())
        }

        exception<ContentTransformationException> { call, cause ->
            when (cause) {
                is UnsupportedMediaTypeException ->
                    call.respondError(HttpStatusCode.UnsupportedMediaType, "Unsupported media type")

                else -> call.respondError(HttpStatusCode.BadRequest, "Malformed request body")
            }
        }

        exception<NotFoundException> { call, _ ->
            call.respondError(HttpStatusCode.NotFound, "Resource not found")
        }

        exception<Throwable> { call, cause ->
            call.application.log.error("Unhandled error: ${call.request.httpMethod.value} ${call.request.uri}", cause)
            call.respondError(HttpStatusCode.InternalServerError, "Internal server error")
        }
    }
}

private inline fun <reified T : Throwable> Throwable.findCause(): T? =
    generateSequence(this) { it.cause }.filterIsInstance<T>().firstOrNull()
