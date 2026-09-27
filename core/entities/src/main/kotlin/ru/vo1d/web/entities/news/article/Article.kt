package ru.vo1d.web.entities.news.article

import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class Article(
    val id: Int? = null,
    val title: String,
    val body: String,
    val previewImage: String? = null,
    val gallery: List<String>? = null,
    val dateTime: Instant? = null,
    val categories: List<Int>
)
