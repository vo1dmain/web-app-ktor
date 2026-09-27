package ru.vo1d.web.entities.qna.question

import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class Question(
    val id: Int? = null,
    val theme: String,
    val body: String,
    val acceptorId: Int,
    val email: String?,
    val dateTime: Instant? = null
)
