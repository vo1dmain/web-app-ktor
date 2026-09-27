package ru.vo1d.web.api.extensions

import ru.vo1d.web.api.errors.InvalidRequestException

/**
 * Returns this collection or throws an exception if matches given [predicate].
 * @throws InvalidRequestException if matches given [predicate]
 */
fun <E, C : Collection<E>> C.failIf(predicate: C.() -> Boolean) = apply {
    if (predicate()) throw InvalidRequestException("Data boundaries violated")
}