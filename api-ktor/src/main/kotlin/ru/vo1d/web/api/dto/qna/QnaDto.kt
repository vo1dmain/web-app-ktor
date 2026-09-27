package ru.vo1d.web.api.dto.qna

import kotlinx.serialization.Serializable
import ru.vo1d.web.domain.qna.answer.Answer
import ru.vo1d.web.domain.qna.post.Post
import ru.vo1d.web.domain.qna.post.PostView
import ru.vo1d.web.domain.qna.question.NewQuestion
import ru.vo1d.web.domain.qna.question.Question
import kotlin.time.Instant

@Serializable
data class QuestionRequest(
    val theme: String,
    val body: String,
    val acceptorId: Int,
    val email: String?
)

@Serializable
data class QuestionResponse(
    val id: Int,
    val theme: String,
    val body: String,
    val acceptorId: Int,
    val email: String?,
    val dateTime: Instant
)

@Serializable
data class AnswerResponse(
    val id: Int,
    val questionId: Int,
    val body: String,
    val dateTime: Instant
)

@Serializable
data class PostResponse(
    val id: Int,
    val question: QuestionResponse,
    val answer: AnswerResponse
)

@Serializable
data class PostSummaryResponse(
    val id: Int,
    val questionId: Int,
    val questionDateTime: Instant,
    val questionTheme: String,
    val answerDateTime: Instant
)

fun QuestionRequest.toDomain() = NewQuestion(theme, body, acceptorId, email)

fun Question.toResponse() = QuestionResponse(id, theme, body, acceptorId, email, dateTime)

fun Answer.toResponse() = AnswerResponse(id, questionId, body, dateTime)

fun Post.toResponse() = PostResponse(id, question.toResponse(), answer.toResponse())

fun PostView.toResponse() = PostSummaryResponse(id, questionId, questionDateTime, questionTheme, answerDateTime)
