package ru.vo1d.web.app

import io.ktor.server.config.*
import kotlinx.datetime.TimeZone
import ru.vo1d.web.persistence.context.DatabaseConfig
import kotlin.io.path.Path

/**
 * Settings from `application.conf` that the rest of the application needs.
 */
internal fun ApplicationConfig.databaseConfig() = DatabaseConfig(
    driver = property("database.driver").getString(),
    newsUrl = property("database.news").getString(),
    qnaUrl = property("database.qna").getString(),
    daybookUrl = property("database.daybook").getString(),
    demoData = propertyOrNull("database.demoData")?.getString().toBoolean(),
    importFile = propertyOrNull("database.importFile")?.getString()?.let(::Path)
)

internal fun ApplicationConfig.daybookTimeZone() = TimeZone.of(property("daybook.timeZone").getString())
