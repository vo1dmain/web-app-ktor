package ru.penzgtu

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import ru.penzgtu.plugins.configureHTTP
import ru.penzgtu.plugins.configureMonitoring
import ru.penzgtu.plugins.configureRouting
import ru.penzgtu.plugins.configureSerialization

fun main() {
    embeddedServer(Netty, port = 8080, host = "localhost") {
        configureRouting()
        configureSerialization()
        configureMonitoring()
        configureHTTP()
    }.start(wait = true)
}
