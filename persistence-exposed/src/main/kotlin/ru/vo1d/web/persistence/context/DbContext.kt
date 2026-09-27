package ru.vo1d.web.persistence.context

import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import ru.vo1d.web.persistence.daybook.DatedSessions
import ru.vo1d.web.persistence.daybook.Groups
import ru.vo1d.web.persistence.daybook.RegularSessions
import ru.vo1d.web.persistence.daybook.TimetableDatedSessions
import ru.vo1d.web.persistence.daybook.TimetableRegularSessions
import ru.vo1d.web.persistence.news.ArticleCategories
import ru.vo1d.web.persistence.qna.Posts

/**
 * JDBC settings of the three databases.
 *
 * @param driver JDBC driver class, shared by all of them
 */
data class DatabaseConfig(
    val driver: String,
    val newsUrl: String,
    val qnaUrl: String,
    val daybookUrl: String
)

/**
 * The databases of each area; connections are opened lazily, on the first transaction.
 */
class DbContext(config: DatabaseConfig) {
    val news = Database.connect(config.newsUrl, config.driver)
    val qna = Database.connect(config.qnaUrl, config.driver)
    val daybook = Database.connect(config.daybookUrl, config.driver)

    /**
     * Creates missing tables (referenced tables are created too); existing tables are left as they are.
     */
    fun init() {
        transaction(news) {
            SchemaUtils.create(ArticleCategories)
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
        }
    }
}
