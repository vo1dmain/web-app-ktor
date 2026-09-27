package ru.vo1d.web.api.extensions

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import ru.vo1d.web.api.errors.ErrorResponse

suspend inline fun ApplicationCall.respondError(status: HttpStatusCode, cause: Exception) {
    respond(status, ErrorResponse(status.value, cause.localizedMessage))
}