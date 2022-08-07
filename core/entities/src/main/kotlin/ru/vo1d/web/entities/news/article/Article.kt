package ru.vo1d.web.entities.news.article

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.serialization.Serializable

@Serializable
data class Article(
    val id: Int? = null,
    val title: String,
    val body: String,
    val previewImage: String? = null,
    val gallery: List<String>? = null,
    val dateTime: LocalDateTime? = null,
    val timeZone: TimeZone? = null,
    val categories: List<Int>
)
