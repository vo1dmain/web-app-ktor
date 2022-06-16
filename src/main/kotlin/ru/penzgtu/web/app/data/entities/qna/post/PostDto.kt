package ru.penzgtu.web.app.data.entities.qna.post

import kotlinx.serialization.Serializable
import ru.penzgtu.web.app.data.entities.qna.Answer
import ru.penzgtu.web.app.data.entities.qna.Question

@Serializable
data class PostDto(
    val id: Int,
    val question: Question,
    val answer: Answer
)