package ru.penzgtu.web.app

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import ru.penzgtu.web.app.plugins.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "localhost") {
        configureDI()
        configureRouting()
        configureSerialization()
        configureMonitoring()
        configureHTTP()
        configureStatusPages()
    }.start(wait = true)
}
