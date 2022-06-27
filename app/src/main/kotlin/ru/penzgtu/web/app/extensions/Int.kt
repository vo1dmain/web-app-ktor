package ru.penzgtu.web.app.extensions

import io.ktor.server.plugins.*

fun Int.failIfNegative(): Int {
    if (this < 0) throw BadRequestException("Request parameter is less than zero")
    return this
}