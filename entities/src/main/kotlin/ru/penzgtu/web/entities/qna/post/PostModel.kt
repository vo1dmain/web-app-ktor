package ru.penzgtu.web.entities.qna.post

import kotlinx.serialization.Serializable
import ru.penzgtu.web.entities.qna.answer.AnswerModel
import ru.penzgtu.web.entities.qna.question.QuestionModel

@Serializable
data class PostModel(
    val id: Int?,
    val question: QuestionModel,
    val answer: AnswerModel
)