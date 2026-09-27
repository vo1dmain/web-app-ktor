package ru.vo1d.web.app

import io.ktor.server.application.*
import org.koin.ktor.ext.getKoin
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import ru.vo1d.web.api.plugins.apiRouting
import ru.vo1d.web.api.routing.daybookRouting
import ru.vo1d.web.api.routing.newsRouting
import ru.vo1d.web.api.routing.qnaRouting
import ru.vo1d.web.persistence.context.DbContext

/**
 * Wires the application: the DI graph, the database and the routes. Runs after the plugin modules.
 */
fun Application.mainModule() {
    val config = environment.config

    install(Koin) {
        slf4jLogger()
        modules(persistenceModule(config.databaseConfig()), domainModule(config.daybookTimeZone()))
    }

    val koin = getKoin()

    koin.get<DbContext>().init()

    apiRouting {
        newsRouting(koin.get(), koin.get())
        qnaRouting(koin.get(), koin.get())
        daybookRouting(koin.get(), koin.get(), koin.get(), koin.get(), koin.get(), koin.get())
    }
}
