package ru.penzgtu.web.app.data.entities.qna

import kotlinx.serialization.Serializable

@Serializable
data class Question(
    val acceptorId: Int,
    val email: String,
    val text: String
)
