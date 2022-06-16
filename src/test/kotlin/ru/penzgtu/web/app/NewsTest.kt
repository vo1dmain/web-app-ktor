package ru.penzgtu.web.app

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.testing.*
import org.kodein.di.bind
import org.kodein.di.ktor.di
import org.kodein.di.singleton
import ru.penzgtu.web.app.data.entities.news.Article
import ru.penzgtu.web.app.data.entities.news.ArticleView
import ru.penzgtu.web.app.data.entities.news.categories.Category
import ru.penzgtu.web.app.data.repos.NewsRepo
import ru.penzgtu.web.app.plugins.configureSerialization
import ru.penzgtu.web.app.plugins.configureStatusPages
import ru.penzgtu.web.app.repos.NewsRepoTest
import ru.penzgtu.web.app.routing.newsRouting
import kotlin.test.Test
import kotlin.test.assertEquals

class NewsTest {
    @Test
    fun testList() = testApplication {
        application {
            newsTest()
        }

        val client = jsonClient()

        client.get("/api/v1/news").apply {
            val list = body<List<ArticleView>>()
            assertEquals(0, list[0].id)
        }

        client.get("api/v1/news?category_id=-1").apply {
            assertEquals(HttpStatusCode.NotFound, call.response.status)
        }
    }

    @Test
    fun testItem() = testApplication {
        application {
            newsTest()
        }

        val client = jsonClient()

        client.get("/api/v1/news/a").apply {
            assertEquals(HttpStatusCode.BadRequest, status)
        }

        client.get("/api/v1/news/0").apply {
            val item = body<Article>()
            assertEquals(0, item.id)
        }
    }

    @Test
    fun testCategories() = testApplication {
        application {
            newsTest()
        }

        val client = jsonClient()

        client.get("/api/v1/news/categories").apply {
            val categories = body<List<Category>>()
            assertEquals(0, categories[0].id)
        }

        client.get("/api/v1/news/categories?parent_id=-1").apply {
            assertEquals(HttpStatusCode.NotFound, call.response.status)
        }
    }

    @Test
    fun testCategory() = testApplication {
        application {
            newsTest()
        }

        val client = jsonClient()

        client.get("/api/v1/news/categories/a").apply {
            assertEquals(HttpStatusCode.BadRequest, status)
        }

        client.get("/api/v1/news/categories/0").apply {
            val item = body<Category>()
            assertEquals(0, item.id)
        }
    }


    private fun Application.newsTest() {
        configureSerialization()
        configureStatusPages()

        di {
            bind<NewsRepo> { singleton { NewsRepoTest() } }
        }

        routing {
            route("/api/v1") {
                newsRouting()
            }
        }
    }
}