package ru.penzgtu.web.entities.qna.posts

import kotlinx.serialization.Serializable
import ru.penzgtu.web.entities.qna.answers.AnswerModel
import ru.penzgtu.web.entities.qna.questions.QuestionModel

@Serializable
data class PostModel(
    val id: Int?,
    val question: QuestionModel,
    val answer: AnswerModel
)