package ru.vo1d.web.entities.news.article

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.serialization.Serializable

@Serializable
data class ArticleView(
    val id: Int,
    val title: String,
    val previewImage: String? = null,
    val dateTime: LocalDateTime,
    val timeZone: TimeZone,
    val categories: List<Int>
)
