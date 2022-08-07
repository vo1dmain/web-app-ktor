package ru.vo1d.web.app

import io.ktor.server.application.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI
import org.kodein.di.ktor.di
import org.kodein.di.singleton
import ru.vo1d.web.data.repos.DaybookRepo
import ru.vo1d.web.data.repos.NewsRepo
import ru.vo1d.web.data.repos.QnaRepo
import ru.vo1d.web.exposed.context.DbContext
import ru.vo1d.web.exposed.context.H2Context
import ru.vo1d.web.exposed.repos.DaybookRepoXp
import ru.vo1d.web.exposed.repos.NewsRepoXp
import ru.vo1d.web.exposed.repos.QnaRepoXp
import ru.vo1d.web.exposed.utils.DataFetcherRes

fun Application.mainModule() {
    di {
        bind<NewsRepo> { singleton { NewsRepoXp(H2Context) } }
        bind<QnaRepo> { singleton { QnaRepoXp(H2Context) } }
        bind<DaybookRepo> { singleton { DaybookRepoXp(H2Context) } }
        bind<DbContext> { singleton { H2Context } }
    }

    val dbContext by closestDI().instance<DbContext>()
    dbContext.init()
    runBlocking(Dispatchers.IO) {
        dbContext { queryDatebook { DataFetcherRes.fetchDaybook() } }
        dbContext { queryNews { DataFetcherRes.fetchNews() } }
    }
}
