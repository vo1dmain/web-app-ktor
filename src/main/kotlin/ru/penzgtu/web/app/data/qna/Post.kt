package ru.penzgtu.web.app.data.qna

import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val id: Int,
    val questionDateTime: Long,
    val questionText: String,
    val answerDateTime: Long,
    val answerText: String
)
