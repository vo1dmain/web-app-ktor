package ru.penzgtu.web.app.data.entities.qna.answers

import kotlinx.serialization.Serializable

@Serializable
data class Answer(
    val id: Int?,
    val questionId: Int,
    val text: String,
    val dateTime: Long
)