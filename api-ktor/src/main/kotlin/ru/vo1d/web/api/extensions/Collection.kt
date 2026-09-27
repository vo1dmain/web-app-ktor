package ru.vo1d.web.api.extensions

import io.ktor.server.plugins.*
import ru.vo1d.web.api.errors.InvalidRequestException

/**
 * Returns this collection or throws an exception if it's empty.
 * @throws NotFoundException if collection is empty.
 */
fun <E, C : Collection<E>> C.failIfEmpty() = apply {
    if (isEmpty()) throw NotFoundException()
}

/**
 * Returns this collection or throws an exception if matches given [predicate].
 * @throws InvalidRequestException if matches given [predicate]
 */
fun <E, C : Collection<E>> C.failIf(predicate: C.() -> Boolean) = apply {
    if (predicate()) throw InvalidRequestException("Data boundaries violated")
}