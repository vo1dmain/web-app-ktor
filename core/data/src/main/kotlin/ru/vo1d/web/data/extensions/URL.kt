package ru.vo1d.web.data.extensions

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.net.URL

suspend fun <T> URL.open(block: InputStream.() -> T) = withContext(Dispatchers.IO) {
    this@open.openStream().use {
        it.block()
    }
}