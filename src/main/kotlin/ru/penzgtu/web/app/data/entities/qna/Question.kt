package ru.penzgtu.web.app.data.entities.qna

import kotlinx.serialization.Serializable

@Serializable
data class Question(
    val id: Int?,
    val theme: String,
    val text: String,
    val acceptorId: Int,
    val email: String?,
    val dateTime: Long
)
