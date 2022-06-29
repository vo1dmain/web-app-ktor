package ru.vo1d.web.app.extensions

import io.ktor.server.plugins.*
import java.io.FileNotFoundException

/**
 * Returns this object or throws an exception if it's null
 * @throws NotFoundException if object is null
 */
fun <T : Any?> T?.orFail() = this ?: throw NotFoundException()

/**
 * Shortcut for [java.lang.Class.getResource]
 * @throws FileNotFoundException if file not found
 */
fun <T : Any> T.resource(path: String) = this.javaClass.getResource(path) ?: throw FileNotFoundException()