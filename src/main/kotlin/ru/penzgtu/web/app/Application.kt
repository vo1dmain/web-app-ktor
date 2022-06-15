package ru.penzgtu.web.app

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.kodein.di.bind
import org.kodein.di.ktor.di
import org.kodein.di.singleton
import ru.penzgtu.web.app.data.news.repo.newsRepo
import ru.penzgtu.web.app.plugins.*
import ru.penzgtu.web.app.repos.impl.qnaRepo
import ru.penzgtu.web.app.repos.impl.timetablesRepo

fun main() {
    embeddedServer(Netty, port = 8080, host = "localhost") {
        configureRouting()
        configureSerialization()
        configureMonitoring()
        configureHTTP()
        configureStatusPages()

        di {
            bind { singleton { newsRepo() } }
            bind { singleton { qnaRepo() } }
            bind { singleton { timetablesRepo() } }
        }
    }.start(wait = true)
}
