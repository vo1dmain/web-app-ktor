package ru.penzgtu.web.app.plugins

import io.ktor.server.application.*
import org.kodein.di.bind
import org.kodein.di.ktor.di
import org.kodein.di.singleton
import ru.penzgtu.web.app.data.news.repo.newsRepo
import ru.penzgtu.web.app.repos.impl.qnaRepo
import ru.penzgtu.web.app.repos.impl.timetablesRepo

fun Application.configureDI() {
    di {
        bind { singleton { newsRepo() } }
        bind { singleton { qnaRepo() } }
        bind { singleton { timetablesRepo() } }
    }
}