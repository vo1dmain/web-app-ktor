package ru.vo1d.web.api.extensions

import ru.vo1d.web.api.errors.InvalidRequestException

fun Int.failIfNegative() = apply {
    if (this < 0) throw InvalidRequestException("Request parameter is less than zero")
}

fun Int.failIfNotPositive() = apply {
    if (this < 1) throw InvalidRequestException("Request parameter is less than one")
}
