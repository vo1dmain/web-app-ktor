package ru.penzgtu.web.app.exposed.orm.db

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import ru.penzgtu.web.app.exposed.orm.news.ArticleCategories
import ru.penzgtu.web.app.exposed.orm.news.Articles
import ru.penzgtu.web.app.exposed.orm.news.Categories
import ru.penzgtu.web.app.exposed.orm.qna.Answers
import ru.penzgtu.web.app.exposed.orm.qna.Posts
import ru.penzgtu.web.app.exposed.orm.qna.Questions

object H2Manager : DbManager {
    private lateinit var newsDatabase: Database
    private lateinit var qnaDatabase: Database

    override val newsDb get() = newsDatabase
    override val qnaDb get() = qnaDatabase
    override val timetableDb: Database? = null

    override fun init() {
        newsDatabase = Database.connect("jdbc:h2:mem:newsDb;DB_CLOSE_DELAY=-1;", "org.h2.Driver")
        qnaDatabase = Database.connect("jdbc:h2:mem:qnaDb;DB_CLOSE_DELAY=-1;", "org.h2.Driver")

        transaction(newsDb) {
            SchemaUtils.create(
                Articles,
                Categories,
                ArticleCategories
            )
        }

        transaction(qnaDb) {
            SchemaUtils.create(
                Questions,
                Answers,
                Posts
            )
        }
    }
}