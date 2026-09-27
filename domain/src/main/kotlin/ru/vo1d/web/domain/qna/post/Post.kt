package ru.vo1d.web.domain.qna.post

import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val id: Int? = null,
    val questionId: Int,
    val answerId: Int
)
