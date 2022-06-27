package ru.vo1d.web.entities.qna.answer

import kotlinx.serialization.Serializable

@Serializable
data class AnswerModel(
    val id: Int? = null,
    val questionId: Int,
    val body: String,
    val dateTime: Long
)