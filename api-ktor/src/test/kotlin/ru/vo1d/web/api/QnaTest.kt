package ru.vo1d.web.api

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.testing.*
import org.junit.Test
import ru.vo1d.web.api.dto.qna.PostResponse
import ru.vo1d.web.api.dto.qna.PostSummaryResponse
import ru.vo1d.web.api.dto.qna.QuestionResponse
import ru.vo1d.web.api.fakes.FakePostRepository
import ru.vo1d.web.api.fakes.FakeQuestionRepository
import ru.vo1d.web.api.routing.qnaRouting
import kotlin.test.assertEquals

class QnaTest {
    @Test
    fun testPosts() = testApplication {
        application {
            qnaTest()
        }

        val client = jsonClient()

        client.get("/qna/posts").apply {
            println(bodyAsText())
            val list = body<List<PostSummaryResponse>>()
            assertEquals(0, list[0].id)
        }

        client.get("/qna/posts?page=-1").apply {
            assertEquals(HttpStatusCode.BadRequest, call.response.status)
        }
    }

    @Test
    fun testPost() = testApplication {
        application {
            qnaTest()
        }

        val client = jsonClient()

        client.get("/qna/posts/0").apply {
            println(bodyAsText())
            val item = body<PostResponse>()
            assertEquals(0, item.id)
            assertEquals(0, item.question.id)
            assertEquals(item.question.id, item.answer.questionId)
        }

        client.get("/qna/posts/a").apply {
            assertEquals(HttpStatusCode.BadRequest, status)
        }
    }

    @Test
    fun testQuestions() = testApplication {
        application {
            qnaTest()
        }

        val client = jsonClient()

        client.get("/qna/questions").apply {
            println(bodyAsText())
            val list = body<List<QuestionResponse>>()
            assertEquals(0, list[0].id)
        }

        client.get("/qna/questions?page=-1").apply {
            assertEquals(HttpStatusCode.BadRequest, call.response.status)
        }
    }


    private fun Application.qnaTest() {
        testPlugins()

        routing {
            qnaRouting(FakePostRepository(), FakeQuestionRepository())
        }
    }
}