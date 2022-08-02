package ru.vo1d.web.data.extensions

import java.io.FileNotFoundException

/**
 * Shortcut for [java.lang.Class.getResource]
 * @throws FileNotFoundException if file not found
 */
fun <T : Any> T.resource(path: String) = this.javaClass.getResource(path) ?: throw FileNotFoundException()