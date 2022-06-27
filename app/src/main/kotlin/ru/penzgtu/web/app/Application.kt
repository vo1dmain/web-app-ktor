package ru.penzgtu.web.app

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI
import ru.penzgtu.web.app.exposed.db.DbManager
import ru.penzgtu.web.app.plugins.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "localhost") {
        configureDI()
        configureRouting()
        configureSerialization()
        configureMonitoring()
        configureHTTP()
        configureStatusPages()

        val dbManager by closestDI().instance<DbManager>()
        dbManager.init()
    }.start(wait = true)
}
