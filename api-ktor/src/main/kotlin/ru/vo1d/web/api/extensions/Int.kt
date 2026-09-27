package ru.vo1d.web.api.extensions

import io.ktor.server.plugins.*

fun Int.failIfNegative() = apply {
    if (this < 0) throw BadRequestException("Request parameter is less than zero")
}

fun Int.failIfNotPositive() = apply {
    if (this < 1) throw BadRequestException("Request parameter is less than one")
}
