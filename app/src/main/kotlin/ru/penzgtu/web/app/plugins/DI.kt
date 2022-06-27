package ru.penzgtu.web.app.plugins

import io.ktor.server.application.*
import org.kodein.di.bind
import org.kodein.di.ktor.di
import org.kodein.di.singleton
import ru.penzgtu.web.app.data.repos.DaybookRepo
import ru.penzgtu.web.app.data.repos.NewsRepo
import ru.penzgtu.web.app.data.repos.QnaRepo
import ru.penzgtu.web.app.exposed.orm.db.H2Manager
import ru.penzgtu.web.app.exposed.repos.DaybookRepoXp
import ru.penzgtu.web.app.exposed.repos.NewsRepoXp
import ru.penzgtu.web.app.exposed.repos.QnaRepoXp

fun Application.configureDI() {
    di {
        bind<NewsRepo> { singleton { NewsRepoXp(H2Manager) } }
        bind<QnaRepo> { singleton { QnaRepoXp(H2Manager) } }
        bind<DaybookRepo> { singleton { DaybookRepoXp(H2Manager) } }
    }
}