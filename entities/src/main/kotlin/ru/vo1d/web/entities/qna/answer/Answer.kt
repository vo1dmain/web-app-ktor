package ru.vo1d.web.entities.qna.answer

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.serialization.Serializable

@Serializable
data class Answer(
    val id: Int? = null,
    val questionId: Int,
    val body: String,
    val dateTime: LocalDateTime? = null,
    val timeZone: TimeZone? = null
)