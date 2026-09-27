package ru.vo1d.web.domain.qna.post

import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class PostView(
    val id: Int,
    val questionId: Int,
    val questionDateTime: Instant,
    val questionTheme: String,
    val answerDateTime: Instant
)
