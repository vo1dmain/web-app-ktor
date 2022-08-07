package ru.vo1d.web.exposed.context

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import ru.vo1d.web.exposed.entities.daybook.group.Groups
import ru.vo1d.web.exposed.entities.daybook.timetable.DatedSessions
import ru.vo1d.web.exposed.entities.daybook.timetable.RegularSessions
import ru.vo1d.web.exposed.entities.daybook.timetable.TimetableDatedSessions
import ru.vo1d.web.exposed.entities.daybook.timetable.TimetableRegularSessions
import ru.vo1d.web.exposed.entities.news.ArticleCategories
import ru.vo1d.web.exposed.entities.qna.Posts


object H2Context : DbContext() {
    private lateinit var newsDatabase: Database
    private lateinit var qnaDatabase: Database
    private lateinit var daybookDatabase: Database

    override val newsDb get() = newsDatabase
    override val qnaDb get() = qnaDatabase
    override val daybookDb get() = daybookDatabase

    override fun init() {
        newsDatabase = Database.connect("jdbc:h2:mem:newsDb;DB_CLOSE_DELAY=-1;", "org.h2.Driver")
        qnaDatabase = Database.connect("jdbc:h2:mem:qnaDb;DB_CLOSE_DELAY=-1;", "org.h2.Driver")
        daybookDatabase = Database.connect("jdbc:h2:file:./build/daybook;MODE=MYSQL", "org.h2.Driver")

        transaction(newsDb) {
            SchemaUtils.create(ArticleCategories)
        }

        transaction(qnaDb) {
            SchemaUtils.create(Posts)
        }

        transaction(daybookDb) {
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