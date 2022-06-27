package ru.penzgtu.web.entities.qna.questions

import kotlinx.serialization.Serializable

@Serializable
data class QuestionModel(
    val id: Int?,
    val theme: String,
    val body: String,
    val acceptorId: Int,
    val email: String?,
    val dateTime: Long
)
