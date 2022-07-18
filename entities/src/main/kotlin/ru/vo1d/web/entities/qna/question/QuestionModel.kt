package ru.vo1d.web.entities.qna.question

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.serialization.Serializable

@Serializable
data class QuestionModel(
    val id: Int? = null,
    val theme: String,
    val body: String,
    val acceptorId: Int,
    val email: String?,
    val dateTime: LocalDateTime? = null,
    val timeZone: TimeZone? = null
)
