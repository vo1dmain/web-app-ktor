package ru.vo1d.web.app

import io.ktor.server.application.*
import org.koin.ktor.ext.getKoin
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import ru.vo1d.web.api.installApi
import ru.vo1d.web.api.routing.daybookRouting
import ru.vo1d.web.api.routing.newsRouting
import ru.vo1d.web.api.routing.qnaRouting
import ru.vo1d.web.persistence.context.DbContext

/**
 * The application module: the DI graph, the databases and the HTTP layer with its routes.
 */
fun Application.mainModule() {
    val config = environment.config

    install(Koin) {
        slf4jLogger()
        modules(persistenceModule(config.databaseConfig()), domainModule(config.daybookTimeZone()))
    }

    val koin = getKoin()

    koin.get<DbContext>().init()

    installApi {
        newsRouting(koin.get(), koin.get())
        qnaRouting(koin.get(), koin.get())
        daybookRouting(koin.get(), koin.get(), koin.get(), koin.get(), koin.get(), koin.get())
    }
}
