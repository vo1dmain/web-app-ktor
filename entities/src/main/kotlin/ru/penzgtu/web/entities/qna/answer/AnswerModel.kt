package ru.penzgtu.web.entities.qna.answer

import kotlinx.serialization.Serializable

@Serializable
data class AnswerModel(
    val id: Int?,
    val questionId: Int,
    val body: String,
    val dateTime: Long
)