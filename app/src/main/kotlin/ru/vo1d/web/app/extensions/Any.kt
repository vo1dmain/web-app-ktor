package ru.vo1d.web.app.extensions

import io.ktor.server.plugins.*

/**
 * Returns this object or throws an exception if it's null
 * @throws NotFoundException if object is null
 */
fun <T : Any?> T?.orFail(): T {
    return this ?: throw NotFoundException()
}