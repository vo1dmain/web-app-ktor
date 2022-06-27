package ru.penzgtu.web.app.exposed.db

import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.transactions.transaction
import ru.penzgtu.web.app.exposed.orm.news.ArticleCategories
import ru.penzgtu.web.app.exposed.orm.news.Articles
import ru.penzgtu.web.app.exposed.orm.news.Categories
import ru.penzgtu.web.app.exposed.orm.qna.Answers
import ru.penzgtu.web.app.exposed.orm.qna.Posts
import ru.penzgtu.web.app.exposed.orm.qna.Questions
import ru.penzgtu.web.app.extensions.open
import ru.penzgtu.web.entities.news.article.ArticleModel
import ru.penzgtu.web.entities.news.category.CategoryModel

@OptIn(ExperimentalSerializationApi::class)
object H2Manager : DbManager {
    private lateinit var newsDatabase: Database
    private lateinit var qnaDatabase: Database
    private lateinit var timetableDatabase: Database

    override val newsDb get() = newsDatabase
    override val qnaDb get() = qnaDatabase
    override val timetableDb get() = timetableDatabase

    private val json = Json { ignoreUnknownKeys = true }

    override fun init() {
        newsDatabase = Database.connect("jdbc:h2:mem:newsDb;DB_CLOSE_DELAY=-1;", "org.h2.Driver")
        qnaDatabase = Database.connect("jdbc:h2:mem:qnaDb;DB_CLOSE_DELAY=-1;", "org.h2.Driver")
        timetableDatabase = Database.connect("jdbc:h2:mem:ttDb;DB_CLOSE_DELAY=-1;", "org.h2.Driver")

        transaction(newsDb) {
            SchemaUtils.create(
                Articles,
                Categories,
                ArticleCategories
            )
            prefetchNews()
        }

        transaction(qnaDb) {
            SchemaUtils.create(
                Questions,
                Answers,
                Posts
            )
        }
    }

    private fun prefetchNews() {
        val categoriesFile = this.javaClass.getResource("/categories.json")!!
        val categories = runBlocking {
            categoriesFile.open {
                json.decodeFromStream<List<CategoryModel>>(this@open)
            }
        }

        Categories.batchInsert(categories, shouldReturnGeneratedValues = false) {
            this[Categories.title] = it.title
            this[Categories.parentId] = it.parentId
        }

        val newsFile = this.javaClass.getResource("/news_list.json")!!
        val news = runBlocking {
            newsFile.open {
                json.decodeFromStream<List<ArticleModel>>(this@open)
            }
        }

        news.forEach { item ->
            val itemId = Articles.insertAndGetId {
                it[title] = item.title
                it[body] = item.body
                it[preview] = item.previewImage
                it[gallery] = item.gallery?.joinToString(",")
                it[dateTime] = item.dateTime
            }.value

            ArticleCategories.batchInsert(item.categories, shouldReturnGeneratedValues = false) { id ->
                this[ArticleCategories.articleId] = itemId
                this[ArticleCategories.categoryId] = id
            }
        }
    }
}