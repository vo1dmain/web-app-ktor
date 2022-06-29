package ru.vo1d.web.entities.qna.post

import kotlinx.serialization.Serializable
import ru.vo1d.web.entities.qna.answer.AnswerModel
import ru.vo1d.web.entities.qna.question.QuestionModel

@Serializable
data class PostDto(
    val id: Int,
    val question: QuestionModel,
    val answer: AnswerModel
)