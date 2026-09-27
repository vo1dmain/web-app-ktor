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
import ru.vo1d.web.domain.errors.AlreadyExistsException
import ru.vo1d.web.domain.errors.MissingReferenceException
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

    @Test
    fun domainErrorsMapToClientErrors() = testApplication {
        application {
            errorsTest()
        }

        val client = jsonClient()

        client.get("/conflict").body<ErrorResponse>().apply {
            assertEquals(409, code)
            assertEquals("Timetable already exists", reason)
        }

        client.get("/missing").body<ErrorResponse>().apply {
            assertEquals(422, code)
            assertEquals("Referenced entity does not exist", reason)
        }
    }

    private fun Application.errorsTest() {
        testPlugins()

        routing {
            newsRouting(FakeArticleRepository(), FakeCategoryRepository())
            qnaRouting(FakePostRepository(), FakeQuestionRepository())
            get("/boom") { error("secret connection string") }
            get("/conflict") { throw AlreadyExistsException("Timetable already exists") }
            get("/missing") { throw MissingReferenceException() }
        }
    }
}
