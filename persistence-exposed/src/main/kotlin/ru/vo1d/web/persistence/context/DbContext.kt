package ru.vo1d.web.persistence.context

import org.jetbrains.exposed.v1.core.DatabaseConfig as ExposedConfig
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import ru.vo1d.web.persistence.daybook.DatedSessions
import ru.vo1d.web.persistence.daybook.Groups
import ru.vo1d.web.persistence.daybook.RegularSessions
import ru.vo1d.web.persistence.daybook.TimetableDatedSessions
import ru.vo1d.web.persistence.daybook.TimetableRegularSessions
import ru.vo1d.web.persistence.daybook.seedDemoGroups
import ru.vo1d.web.persistence.daybook.seedImportedTimetables
import ru.vo1d.web.persistence.daybook.seedReferenceData
import ru.vo1d.web.persistence.news.ArticleCategories
import ru.vo1d.web.persistence.news.seedDemoNews
import ru.vo1d.web.persistence.qna.Posts
import java.nio.file.Path

/**
 * JDBC settings of the three databases.
 *
 * @param driver JDBC driver class, shared by all of them
 * @param demoData insert demo data on start: news into empty tables, groups for the daybook
 * @param importFile groups and timetables exported from elsewhere, loaded on start
 */
data class DatabaseConfig(
    val driver: String,
    val newsUrl: String,
    val qnaUrl: String,
    val daybookUrl: String,
    val demoData: Boolean = false,
    val importFile: Path? = null
)

/**
 * The databases of each area; connections are opened lazily, on the first transaction.
 */
class DbContext(private val config: DatabaseConfig) {
    // Exposed retries a transaction on any SQLException by default, but a constraint violation fails again
    private val exposedConfig = ExposedConfig { defaultMaxAttempts = 1 }

    val news = Database.connect(config.newsUrl, config.driver, databaseConfig = exposedConfig)
    val qna = Database.connect(config.qnaUrl, config.driver, databaseConfig = exposedConfig)
    val daybook = Database.connect(config.daybookUrl, config.driver, databaseConfig = exposedConfig)

    /**
     * Creates missing tables (referenced tables are created too; existing ones are left as they are) and
     * inserts the daybook reference data, plus demo news and groups if [DatabaseConfig.demoData] is set and the
     * timetables of [DatabaseConfig.importFile] if there is one.
     */
    fun init() {
        transaction(news) {
            SchemaUtils.create(ArticleCategories)
            if (config.demoData) seedDemoNews()
        }

        transaction(qna) {
            SchemaUtils.create(Posts)
        }

        transaction(daybook) {
            SchemaUtils.create(
                Groups,
                RegularSessions,
                DatedSessions,
                TimetableRegularSessions,
                TimetableDatedSessions
            )
            seedReferenceData()
            if (config.demoData) seedDemoGroups()
            config.importFile?.let(::seedImportedTimetables)
        }
    }
}
