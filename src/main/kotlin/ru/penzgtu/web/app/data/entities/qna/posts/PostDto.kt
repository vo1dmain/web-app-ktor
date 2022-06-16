package ru.penzgtu.web.app.data.entities.qna.posts

import kotlinx.serialization.Serializable
import ru.penzgtu.web.app.data.entities.qna.answers.Answer
import ru.penzgtu.web.app.data.entities.qna.questions.Question

@Serializable
data class PostDto(
    val id: Int,
    val question: Question,
    val answer: Answer
)