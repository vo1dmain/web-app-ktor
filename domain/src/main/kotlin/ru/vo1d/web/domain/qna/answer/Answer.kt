package ru.vo1d.web.domain.qna.answer

import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class Answer(
    val id: Int? = null,
    val questionId: Int,
    val body: String,
    val dateTime: Instant? = null
)