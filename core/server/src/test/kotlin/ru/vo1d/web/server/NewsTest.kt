package ru.vo1d.web.server

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.testing.*
import org.kodein.di.bind
import org.kodein.di.ktor.di
import org.kodein.di.singleton
import ru.vo1d.web.data.repos.NewsRepo
import ru.vo1d.web.entities.news.article.Article
import ru.vo1d.web.entities.news.article.ArticleView
import ru.vo1d.web.entities.news.category.Category
import ru.vo1d.web.server.routing.newsRouting
import kotlin.test.Test
import kotlin.test.assertEquals

class NewsTest {
    @Test
    fun testList() = testApplication {
        application {
            newsTest()
        }

        val client = jsonClient()

        client.get("/news/articles").apply {
            println(bodyAsText())
            val list = body<List<ArticleView>>()
            assertEquals(0, list[0].id)
        }

        client.get("/news/articles") {
            parameter("category", listOf(-1, -2, -3))
        }.apply {
            assertEquals(HttpStatusCode.BadRequest, call.response.status)
        }
    }

    @Test
    fun testItem() = testApplication {
        application {
            newsTest()
        }

        val client = jsonClient()

        client.get("/news/articles/0").apply {
            println(bodyAsText())
            val item = body<Article>()
            assertEquals(0, item.id)
        }

        client.get("/news/articles/a").apply {
            assertEquals(HttpStatusCode.BadRequest, status)
        }
    }

    @Test
    fun testCategories() = testApplication {
        application {
            newsTest()
        }

        val client = jsonClient()

        client.get("/news/categories").apply {
            println(bodyAsText())
            val categories = body<List<Category>>()
            assertEquals(0, categories[0].id)
        }

        client.get("/news/categories?parent=-1").apply {
            assertEquals(HttpStatusCode.BadRequest, call.response.status)
        }
    }

    @Test
    fun testCategory() = testApplication {
        application {
            newsTest()
        }

        val client = jsonClient()

        client.get("/news/categories/0").apply {
            println(bodyAsText())
            val item = body<Category>()
            assertEquals(0, item.id)
        }

        client.get("/news/categories/a").apply {
            assertEquals(HttpStatusCode.BadRequest, status)
        }
    }


    private fun Application.newsTest() {
        di {
            import(testDaoModule)
            bind<NewsRepo>() with singleton { NewsRepo(di) }
        }

        routing {
            newsRouting()
        }
    }
}