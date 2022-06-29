package ru.vo1d.web.app.exposed.db

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import ru.vo1d.web.app.exposed.orm.daybook.group.Groups
import ru.vo1d.web.app.exposed.orm.daybook.group.TableTypes
import ru.vo1d.web.app.exposed.orm.daybook.timetable.SessionTypes
import ru.vo1d.web.app.exposed.orm.daybook.timetable.TimePeriods
import ru.vo1d.web.app.exposed.orm.daybook.timetable.WeekOptions
import ru.vo1d.web.app.exposed.orm.news.ArticleCategories
import ru.vo1d.web.app.exposed.orm.news.Articles
import ru.vo1d.web.app.exposed.orm.news.Categories
import ru.vo1d.web.app.exposed.orm.qna.Answers
import ru.vo1d.web.app.exposed.orm.qna.Posts
import ru.vo1d.web.app.exposed.orm.qna.Questions
import ru.vo1d.web.app.exposed.utils.DataFetcherRes


object H2Manager : DbManager {
    private lateinit var newsDatabase: Database
    private lateinit var qnaDatabase: Database
    private lateinit var daybookDatabase: Database

    override val newsDb get() = newsDatabase
    override val qnaDb get() = qnaDatabase
    override val daybookDb get() = daybookDatabase

    override fun init() {
        newsDatabase = Database.connect("jdbc:h2:mem:newsDb;DB_CLOSE_DELAY=-1;", "org.h2.Driver")
        qnaDatabase = Database.connect("jdbc:h2:mem:qnaDb;DB_CLOSE_DELAY=-1;", "org.h2.Driver")
        daybookDatabase = Database.connect("jdbc:h2:mem:daybookDb;DB_CLOSE_DELAY=-1;", "org.h2.Driver")

        transaction(newsDb) {
            SchemaUtils.create(Articles, Categories, ArticleCategories)
            DataFetcherRes.fetchNews()
        }

        transaction(qnaDb) {
            SchemaUtils.create(Questions, Answers, Posts)
            DataFetcherRes.fetchQna()
        }

        transaction(daybookDb) {
            SchemaUtils.create(
                SessionTypes,
                TableTypes,
                TimePeriods,
                WeekOptions,
                Groups
            )
            DataFetcherRes.fetchDaybook()
        }
    }
}