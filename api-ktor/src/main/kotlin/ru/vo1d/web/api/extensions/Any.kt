package ru.vo1d.web.api.extensions

import io.ktor.server.plugins.*

/**
 * Returns this object or throws an exception if it's null
 * @throws NotFoundException if object is null
 */
fun <T> T?.orFail() = this ?: throw NotFoundException()