package ru.vo1d.web.app.extensions

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.net.URL

suspend fun <T> URL.open(block: InputStream.() -> T): T {
    return withContext(Dispatchers.IO) {
        this@open.openStream().use {
            it.block()
        }
    }
}