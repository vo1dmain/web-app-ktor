package ru.vo1d.web.app

import io.ktor.server.application.*
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
import ru.vo1d.web.exposed.exposedDaoModule

fun Application.mainModule() {
    di {
        import(exposedDaoModule)

        bind<NewsRepo>() with singleton { NewsRepo(di) }
        bind<QnaRepo>() with singleton { QnaRepo(di) }
        bind<DaybookRepo>() with singleton { DaybookRepo(di) }
        bind<DbContext>() with singleton { H2Context }
    }

    val dbContext by closestDI().instance<DbContext>()
    dbContext.init()
}
