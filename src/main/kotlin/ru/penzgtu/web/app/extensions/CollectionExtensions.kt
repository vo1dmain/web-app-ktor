package ru.penzgtu.web.app.extensions

import io.ktor.server.plugins.*

/**
 * Returns this collection or throws an exception if it's empty
 * @throws NotFoundException if collection is empty
 */
fun <E, C : Collection<E>> C.failIfEmpty() : C {
    return apply {
        if (isEmpty()) throw NotFoundException()
    }
}