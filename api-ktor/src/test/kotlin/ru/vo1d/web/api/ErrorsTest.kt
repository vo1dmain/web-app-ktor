package ru.vo1d.web.api

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.testing.*
import ru.vo1d.web.api.errors.ErrorResponse
import ru.vo1d.web.api.fakes.FakeArticleRepository
import ru.vo1d.web.api.fakes.FakeCategoryRepository
import ru.vo1d.web.api.fakes.FakePostRepository
import ru.vo1d.web.api.fakes.FakeQuestionRepository
import ru.vo1d.web.api.routing.newsRouting
import ru.vo1d.web.api.routing.qnaRouting
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class ErrorsTest {
    @Test
    fun unexpectedErrorHidesDetails() = testApplication {
        application {
            errorsTest()
        }

        client.get("/boom").apply {
            assertEquals(HttpStatusCode.InternalServerError, status)
            assertFalse("secret" in bodyAsText())
        }
    }

    @Test
    fun malformedBodyHidesClassNames() = testApplication {
        application {
            errorsTest()
        }

        val response = jsonClient().post("/qna/questions") {
            contentType(ContentType.Application.Json)
            setBody("""{"theme":"no body"}""")
        }

        assertEquals(HttpStatusCode.BadRequest, response.status)
        assertFalse("ru.vo1d" in response.bodyAsText())
    }

    @Test
    fun ownValidationMessageIsShown() = testApplication {
        application {
            errorsTest()
        }

        val error = jsonClient().get("/news/articles?page=0").body<ErrorResponse>()

        assertEquals(400, error.code)
        assertEquals("Request parameter is less than one", error.reason)
    }

    private fun Application.errorsTest() {
        testPlugins()

        routing {
            newsRouting(FakeArticleRepository(), FakeCategoryRepository())
            qnaRouting(FakePostRepository(), FakeQuestionRepository())
            get("/boom") { error("secret connection string") }
        }
    }
}
