package ru.vo1d.web.api.errors

import io.ktor.server.plugins.*

/**
 * A request rejected by our own validation; unlike other errors, its message is safe to show to the client.
 */
class InvalidRequestException(message: String) : BadRequestException(message)
