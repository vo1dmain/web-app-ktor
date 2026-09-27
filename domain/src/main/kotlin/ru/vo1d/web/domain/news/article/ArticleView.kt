package ru.vo1d.web.domain.news.article

import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class ArticleView(
    val id: Int,
    val title: String,
    val previewImage: String? = null,
    val dateTime: Instant,
    val categories: List<Int>
)
