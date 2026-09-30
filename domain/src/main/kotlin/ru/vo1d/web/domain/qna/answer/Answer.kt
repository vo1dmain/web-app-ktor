package ru.vo1d.web.domain.qna.answer

import kotlin.time.Instant

data class Answer(
    val id: Int,
    val questionId: Int,
    val body: String,
    val dateTime: Instant
)