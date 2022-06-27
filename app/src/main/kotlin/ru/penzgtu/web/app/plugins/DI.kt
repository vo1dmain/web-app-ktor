package ru.penzgtu.web.app.plugins

import io.ktor.server.application.*
import org.kodein.di.bind
import org.kodein.di.ktor.di
import org.kodein.di.singleton
import ru.penzgtu.web.app.data.repos.NewsRepo
import ru.penzgtu.web.app.data.repos.QnaRepo
import ru.penzgtu.web.app.data.repos.TimetablesRepo
import ru.penzgtu.web.app.exposed.repos.NewsRepoXp
import ru.penzgtu.web.app.exposed.repos.QnaRepoXp
import ru.penzgtu.web.app.exposed.repos.TimetablesRepoXp

fun Application.configureDI() {
    di {
        bind<NewsRepo> { singleton { NewsRepoXp() } }
        bind<QnaRepo> { singleton { QnaRepoXp() } }
        bind<TimetablesRepo> { singleton { TimetablesRepoXp() } }
    }
}