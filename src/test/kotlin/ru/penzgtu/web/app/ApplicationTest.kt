package ru.penzgtu.web.app

import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import org.kodein.di.bind
import org.kodein.di.ktor.di
import org.kodein.di.singleton
import ru.penzgtu.web.app.data.news.Article
import ru.penzgtu.web.app.data.news.ArticleView
import ru.penzgtu.web.app.data.news.categories.Category
import ru.penzgtu.web.app.plugins.configureRouting
import ru.penzgtu.web.app.plugins.configureSerialization
import ru.penzgtu.web.app.plugins.configureStatusPages
import ru.penzgtu.web.app.repos.NewsRepo
import ru.penzgtu.web.app.repos.NewsRepoImplTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {
    @Test
    fun testNews() = testApplication {
        application {
            configureSerialization()
            configureRouting()
            di {
                bind<NewsRepo> { singleton { NewsRepoImplTest() } }
            }
        }

        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        client.get("/api/v1/news").apply {
            val list = body<List<ArticleView>>()
            assertEquals(0, list[0].id)
        }

        client.get("api/v1/news?category_id=-1").apply {
            assertEquals(HttpStatusCode.NotFound, call.response.status)
        }
    }

    @Test
    fun testNewsItem() = testApplication {
        application {
            configureSerialization()
            configureRouting()
            configureStatusPages()
            di {
                bind<NewsRepo> { singleton { NewsRepoImplTest() } }
            }
        }

        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

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
            configureSerialization()
            configureRouting()
            configureStatusPages()
            di {
                bind<NewsRepo> { singleton { NewsRepoImplTest() } }
            }
        }

        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        client.get("/api/v1/news/categories").apply {
            val categories = body<List<Category>>()
            assertEquals(0, categories[0].id)
        }

        client.get("/api/v1/news/categories?parent_id=-1").apply {
            assertEquals(HttpStatusCode.NotFound, call.response.status)
        }
    }
}