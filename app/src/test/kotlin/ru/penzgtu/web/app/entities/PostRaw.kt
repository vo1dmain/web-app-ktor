package ru.penzgtu.web.app.entities

import kotlinx.serialization.Serializable

@Serializable
data class PostRaw(
    val id: Int,
    val questionId: Int,
    val answerId: Int
)
