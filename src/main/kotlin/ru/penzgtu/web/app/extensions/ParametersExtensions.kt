package ru.penzgtu.web.app.extensions

import io.ktor.http.*
import io.ktor.server.plugins.*
import io.ktor.server.util.*

inline fun <reified R : Any> Parameters.getOrNull(name: String): R? {
    return try {
        getOrFail<R>(name)
    } catch (e: BadRequestException) {
        null
    }
}

inline fun <reified R : Any> Parameters.getOrDefault(name: String, default: R): R {
    return try {
        getOrFail<R>(name)
    } catch (e: BadRequestException) {
        default
    }
}