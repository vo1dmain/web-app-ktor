package ru.penzgtu.web.entities.qna.post

import kotlinx.serialization.Serializable

@Serializable
data class PostView(
    val id: Int,
    val questionId: Int,
    val questionDateTime: Long,
    val questionTheme: String,
    val answerDateTime: Long
)
