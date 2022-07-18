package ru.vo1d.web.entities.qna.post

import kotlinx.serialization.Serializable

@Serializable
data class PostModel(val id: Int? = null, val questionId: Int, val answerId: Int)
