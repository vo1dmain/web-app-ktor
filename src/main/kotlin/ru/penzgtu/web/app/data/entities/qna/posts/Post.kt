package ru.penzgtu.web.app.data.entities.qna.posts

import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val id: Int?,
    val questionId: Int,
    val answerId: Int
)