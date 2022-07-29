package ru.vo1d.web.app.extensions

import io.ktor.server.plugins.*

/**
 * Returns this collection or throws an exception if it's empty.
 * @throws NotFoundException if collection is empty.
 */
fun <E, C : Collection<E>> C.failIfEmpty() = apply {
    if (isEmpty()) throw NotFoundException()
}

/**
 * Returns this collection or throws an exception if matches given [predicate].
 * @throws BadRequestException if matches given [predicate]
 */
fun <E, C : Collection<E>> C.failIf(predicate: C.() -> Boolean) = apply {
    if (predicate()) throw BadRequestException("Data boundaries violated")
}