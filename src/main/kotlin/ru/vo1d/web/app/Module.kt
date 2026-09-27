package ru.vo1d.web.app

import io.ktor.server.application.*
import kotlinx.datetime.TimeZone
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI
import org.kodein.di.ktor.di
import org.kodein.di.singleton
import ru.vo1d.web.data.repos.DaybookRepo
import ru.vo1d.web.data.repos.NewsRepo
import ru.vo1d.web.data.repos.QnaRepo
import ru.vo1d.web.exposed.context.DbContext
import ru.vo1d.web.exposed.exposedDaoModule

fun Application.mainModule() {
    val timeZone = TimeZone.of(environment.config.property("daybook.timeZone").getString())

    di {
        import(exposedDaoModule)

        bind<NewsRepo>() with singleton { NewsRepo(di) }
        bind<QnaRepo>() with singleton { QnaRepo(di) }
        bind<DaybookRepo>() with singleton { DaybookRepo(di, timeZone) }
    }

    val dbContext by closestDI().instance<DbContext>()
    dbContext.init()
}
