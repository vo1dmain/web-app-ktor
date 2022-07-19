package ru.vo1d.web.app

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
import ru.vo1d.web.app.plugins.configureResources
import ru.vo1d.web.app.plugins.configureSerialization
import ru.vo1d.web.app.plugins.configureStatusPages
import ru.vo1d.web.app.repos.NewsRepoTest
import ru.vo1d.web.app.routing.newsRouting
import ru.vo1d.web.data.repos.impl.NewsRepoImpl
import ru.vo1d.web.entities.news.article.ArticleModel
import ru.vo1d.web.entities.news.article.ArticleView
import ru.vo1d.web.entities.news.category.CategoryModel
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

        client.get("/news/articles?category=-1").apply {
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
        configureResources()

        di {
            bind<NewsRepoImpl> { singleton { NewsRepoTest() } }
        }

        routing {
            newsRouting()
        }
    }
}