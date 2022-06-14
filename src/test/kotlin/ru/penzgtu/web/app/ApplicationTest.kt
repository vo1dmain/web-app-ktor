package ru.penzgtu.web.app

import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import ru.penzgtu.web.app.data.news.ArticleView
import ru.penzgtu.web.app.plugins.configureMocking
import ru.penzgtu.web.app.plugins.configureRouting
import ru.penzgtu.web.app.plugins.configureSerialization
import ru.penzgtu.web.app.plugins.configureStatusPages
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {
    @Test
    fun testNews() = testApplication {
        application {
            configureSerialization()
            configureRouting()
            configureStatusPages()
            configureMocking()
        }

        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        client.get("/api/news").apply {
            val list = body<List<ArticleView>>()
            assertEquals(0, list[0].id)
        }
    }

    @Test
    fun testNewsItem() = testApplication {
        application {
            configureSerialization()
            configureRouting()
            configureStatusPages()
            configureMocking()
        }

        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        client.get("/api/news/a").apply {
            assertEquals(HttpStatusCode.BadRequest, status)
        }
    }
}