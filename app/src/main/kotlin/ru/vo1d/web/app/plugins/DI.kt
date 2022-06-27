package ru.vo1d.web.app.plugins

import io.ktor.server.application.*
import org.kodein.di.bind
import org.kodein.di.ktor.di
import org.kodein.di.singleton
import ru.vo1d.web.app.data.repos.DaybookRepo
import ru.vo1d.web.app.data.repos.NewsRepo
import ru.vo1d.web.app.data.repos.QnaRepo
import ru.vo1d.web.app.exposed.db.DbManager
import ru.vo1d.web.app.exposed.db.H2Manager
import ru.vo1d.web.app.exposed.repos.DaybookRepoXp
import ru.vo1d.web.app.exposed.repos.NewsRepoXp
import ru.vo1d.web.app.exposed.repos.QnaRepoXp

fun Application.configureDI() {
    di {
        bind<NewsRepo> { singleton { NewsRepoXp(H2Manager) } }
        bind<QnaRepo> { singleton { QnaRepoXp(H2Manager) } }
        bind<DaybookRepo> { singleton { DaybookRepoXp(H2Manager) } }
        bind<DbManager> { singleton { H2Manager } }
    }
}