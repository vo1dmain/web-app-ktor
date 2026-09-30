package ru.vo1d.web.domain.errors

/**
 * The entity being added already exists.
 */
class AlreadyExistsException(message: String, cause: Throwable? = null) : RuntimeException(message, cause)

/**
 * The request refers to an entity that doesn't exist: a group, a type, a session…
 */
class MissingReferenceException(
    message: String = "Referenced entity does not exist",
    cause: Throwable? = null
) : RuntimeException(message, cause)
