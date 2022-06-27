package ru.penzgtu.web.app.exposed.orm

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import ru.penzgtu.web.app.exposed.orm.news.ArticleCategories
import ru.penzgtu.web.app.exposed.orm.news.Articles
import ru.penzgtu.web.app.exposed.orm.news.Categories
import ru.penzgtu.web.app.exposed.orm.qna.Answers
import ru.penzgtu.web.app.exposed.orm.qna.Posts
import ru.penzgtu.web.app.exposed.orm.qna.Questions

object H2Factory {
    fun init() {
        val newsDb = Database.connect("jdbc:h2:file:./build/newsDb", "org.h2.Driver")
        val qnaDb = Database.connect("jdbc:h2:file:./build/qnaDb", "org.h2.Driver")

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