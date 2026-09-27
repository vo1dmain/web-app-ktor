package ru.vo1d.web.app

import io.ktor.server.application.*
import kotlinx.datetime.TimeZone
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI
import org.kodein.di.ktor.di
import org.kodein.di.singleton
import ru.vo1d.web.domain.daybook.DaybookService
import ru.vo1d.web.persistence.context.DbContext
import ru.vo1d.web.persistence.persistenceModule

fun Application.mainModule() {
    val timeZone = TimeZone.of(environment.config.property("daybook.timeZone").getString())

    di {
        import(persistenceModule)

        bind<DaybookService>() with singleton { DaybookService(instance(), timeZone) }
    }

    val dbContext by closestDI().instance<DbContext>()
    dbContext.init()
}
