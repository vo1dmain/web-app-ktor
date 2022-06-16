package ru.penzgtu.web.app.plugins

import io.ktor.server.application.*
import org.kodein.di.bind
import org.kodein.di.ktor.di
import org.kodein.di.singleton
import ru.penzgtu.web.app.data.repos.NewsRepo
import ru.penzgtu.web.app.data.repos.QnaRepo
import ru.penzgtu.web.app.data.repos.TimetablesRepo
import ru.penzgtu.web.app.data.repos.base.NewsRepoBase
import ru.penzgtu.web.app.data.repos.base.QnaRepoBase
import ru.penzgtu.web.app.data.repos.base.TimetablesRepoBase

fun Application.configureDI() {
    di {
        bind<NewsRepo> { singleton { NewsRepoBase() } }
        bind<QnaRepo> { singleton { QnaRepoBase() } }
        bind<TimetablesRepo> { singleton { TimetablesRepoBase() } }
    }
}