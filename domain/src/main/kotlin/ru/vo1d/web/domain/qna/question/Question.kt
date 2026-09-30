package ru.vo1d.web.domain.qna.question

import kotlin.time.Instant

data class Question(
    val id: Int,
    val theme: String,
    val body: String,
    val acceptorId: Int,
    val email: String?,
    val dateTime: Instant
)

data class NewQuestion(
    val theme: String,
    val body: String,
    val acceptorId: Int,
    val email: String?
)
