package ru.vo1d.web.persistence.context

import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import ru.vo1d.web.persistence.daybook.Groups
import ru.vo1d.web.persistence.daybook.DatedSessions
import ru.vo1d.web.persistence.daybook.RegularSessions
import ru.vo1d.web.persistence.daybook.TimetableDatedSessions
import ru.vo1d.web.persistence.daybook.TimetableRegularSessions
import ru.vo1d.web.persistence.news.ArticleCategories
import ru.vo1d.web.persistence.qna.Posts

object H2Context : DbContext() {
    override val news = Database.connect("jdbc:h2:mem:newsDb;DB_CLOSE_DELAY=-1;", "org.h2.Driver")
    override val qna = Database.connect("jdbc:h2:mem:qnaDb;DB_CLOSE_DELAY=-1;", "org.h2.Driver")
    override val daybook = Database.connect("jdbc:h2:file:./build/daybook;MODE=MYSQL", "org.h2.Driver")

    override fun init() {
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
