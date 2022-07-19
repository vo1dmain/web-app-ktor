package ru.vo1d.web.app

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI
import ru.vo1d.web.app.plugins.*
import ru.vo1d.web.orm.db.DbManager

fun main() {
    embeddedServer(Netty, port = 8080, host = "localhost") {
        configureDI()
        configureResources()
        configureRouting()
        configureSerialization()
        configureMonitoring()
        configureHTTP()
        configureStatusPages()

        val dbManager by closestDI().instance<DbManager>()
        dbManager.init()
    }.start(wait = true)
}
