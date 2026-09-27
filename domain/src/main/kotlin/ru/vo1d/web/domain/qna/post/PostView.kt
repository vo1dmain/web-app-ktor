package ru.vo1d.web.domain.qna.post

import kotlin.time.Instant

data class PostView(
    val id: Int,
    val questionId: Int,
    val questionDateTime: Instant,
    val questionTheme: String,
    val answerDateTime: Instant
)
