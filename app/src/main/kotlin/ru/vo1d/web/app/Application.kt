package ru.vo1d.web.app

import io.ktor.server.application.*
import io.ktor.server.netty.*
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI
import org.kodein.di.ktor.di
import org.kodein.di.singleton
import ru.vo1d.web.data.repos.DaybookRepo
import ru.vo1d.web.data.repos.NewsRepo
import ru.vo1d.web.data.repos.QnaRepo
import ru.vo1d.web.orm.db.DbManager
import ru.vo1d.web.orm.db.H2Manager
import ru.vo1d.web.orm.repos.DaybookRepoXp
import ru.vo1d.web.orm.repos.NewsRepoXp
import ru.vo1d.web.orm.repos.QnaRepoXp

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.mainModule() {
    di {
        bind<NewsRepo> { singleton { NewsRepoXp(H2Manager) } }
        bind<QnaRepo> { singleton { QnaRepoXp(H2Manager) } }
        bind<DaybookRepo> { singleton { DaybookRepoXp(H2Manager) } }
        bind<DbManager> { singleton { H2Manager } }
    }

    val dbManager by closestDI().instance<DbManager>()
    dbManager.init()
}
