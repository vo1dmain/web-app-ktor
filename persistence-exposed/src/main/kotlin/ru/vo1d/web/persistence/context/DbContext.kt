package ru.vo1d.web.persistence.context

import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import ru.vo1d.web.persistence.daybook.DatedSessions
import ru.vo1d.web.persistence.daybook.Groups
import ru.vo1d.web.persistence.daybook.RegularSessions
import ru.vo1d.web.persistence.daybook.TimetableDatedSessions
import ru.vo1d.web.persistence.daybook.TimetableRegularSessions
import ru.vo1d.web.persistence.daybook.seedReferenceData
import ru.vo1d.web.persistence.news.ArticleCategories
import ru.vo1d.web.persistence.news.seedDemoNews
import ru.vo1d.web.persistence.qna.Posts

/**
 * JDBC settings of the three databases.
 *
 * @param driver JDBC driver class, shared by all of them
 * @param demoData fill empty news tables with demo articles on start
 */
data class DatabaseConfig(
    val driver: String,
    val newsUrl: String,
    val qnaUrl: String,
    val daybookUrl: String,
    val demoData: Boolean = false
)

/**
 * The databases of each area; connections are opened lazily, on the first transaction.
 */
class DbContext(private val config: DatabaseConfig) {
    val news = Database.connect(config.newsUrl, config.driver)
    val qna = Database.connect(config.qnaUrl, config.driver)
    val daybook = Database.connect(config.daybookUrl, config.driver)

    /**
     * Creates missing tables (referenced tables are created too; existing ones are left as they are) and
     * inserts the daybook reference data, plus demo news if [DatabaseConfig.demoData] is set.
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
        }
    }
}
