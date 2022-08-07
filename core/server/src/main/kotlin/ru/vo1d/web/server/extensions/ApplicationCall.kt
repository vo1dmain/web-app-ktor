package ru.vo1d.web.server.extensions

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import ru.vo1d.web.entities.errors.ErrorResponse

suspend inline fun ApplicationCall.respondError(status: HttpStatusCode, cause: Exception) {
    respond(status, ErrorResponse(status.value, cause.localizedMessage))
}