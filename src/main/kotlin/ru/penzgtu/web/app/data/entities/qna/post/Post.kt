package ru.penzgtu.web.app.data.entities.qna.post

import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val id: Int?,
    val questionId: Int,
    val answerId: Int
)