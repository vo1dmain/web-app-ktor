package ru.vo1d.web.api

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.testing.*
import ru.vo1d.web.api.dto.news.ArticleResponse
import ru.vo1d.web.api.dto.news.ArticleSummaryResponse
import ru.vo1d.web.api.dto.news.CategoryResponse
import ru.vo1d.web.api.fakes.FakeArticleRepository
import ru.vo1d.web.api.fakes.FakeCategoryRepository
import ru.vo1d.web.api.routing.newsRouting
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
            val list = body<List<ArticleSummaryResponse>>()
            assertEquals(0, list[0].id)
        }

        client.get("/news/articles") {
            parameter("category", listOf(-1, -2, -3))
        }.apply {
            assertEquals(HttpStatusCode.BadRequest, call.response.status)
        }

        client.get("/news/articles") {
            parameter("category", listOf(1, -5))
        }.apply {
            assertEquals(HttpStatusCode.BadRequest, call.response.status)
        }

        client.get("/news/articles?category=99").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals(emptyList(), body<List<ArticleSummaryResponse>>())
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
            val item = body<ArticleResponse>()
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
            val categories = body<List<CategoryResponse>>()
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
            val item = body<CategoryResponse>()
            assertEquals(0, item.id)
        }

        client.get("/news/categories/a").apply {
            assertEquals(HttpStatusCode.BadRequest, status)
        }
    }


    private fun Application.newsTest() {
        testPlugins()

        routing {
            newsRouting(FakeArticleRepository(), FakeCategoryRepository())
        }
    }
}