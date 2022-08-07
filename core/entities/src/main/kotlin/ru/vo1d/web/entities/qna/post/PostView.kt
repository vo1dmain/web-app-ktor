package ru.vo1d.web.entities.qna.post

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.serialization.Serializable

@Serializable
data class PostView(
    val id: Int,
    val questionId: Int,
    val questionDateTime: LocalDateTime,
    val questionTimeZone: TimeZone,
    val questionTheme: String,
    val answerDateTime: LocalDateTime,
    val answerTimeZone: TimeZone
)
