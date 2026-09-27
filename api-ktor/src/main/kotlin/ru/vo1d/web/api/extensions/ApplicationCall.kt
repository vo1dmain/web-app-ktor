package ru.vo1d.web.api.extensions

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import ru.vo1d.web.api.errors.ErrorResponse

/**
 * @param reason shown to the client as is, so it must not carry internal details
 */
suspend fun ApplicationCall.respondError(status: HttpStatusCode, reason: String) {
    respond(status, ErrorResponse(status.value, reason))
}
