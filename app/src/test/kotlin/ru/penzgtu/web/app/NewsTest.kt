package ru.penzgtu.web.app

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
import ru.penzgtu.web.app.data.repos.NewsRepo
import ru.penzgtu.web.app.plugins.configureSerialization
import ru.penzgtu.web.app.plugins.configureStatusPages
import ru.penzgtu.web.app.repos.NewsRepoTest
import ru.penzgtu.web.app.routing.newsRouting
import ru.penzgtu.web.entities.news.article.ArticleModel
import ru.penzgtu.web.entities.news.article.ArticleView
import ru.penzgtu.web.entities.news.category.CategoryModel
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

        client.get("/news/articles?category_id=-1").apply {
            assertEquals(HttpStatusCode.NotFound, call.response.status)
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
            val item = body<ArticleModel>()
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
            val categories = body<List<CategoryModel>>()
            assertEquals(0, categories[0].id)
        }

        client.get("/news/categories?parent_id=-1").apply {
            assertEquals(HttpStatusCode.NotFound, call.response.status)
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
            val item = body<CategoryModel>()
            assertEquals(0, item.id)
        }

        client.get("/news/categories/a").apply {
            assertEquals(HttpStatusCode.BadRequest, status)
        }
    }


    private fun Application.newsTest() {
        configureSerialization()
        configureStatusPages()

        di {
            bind<NewsRepo> { singleton { NewsRepoTest() } }
        }

        routing {
            newsRouting()
        }
    }
}