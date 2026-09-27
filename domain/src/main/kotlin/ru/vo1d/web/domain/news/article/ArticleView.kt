package ru.vo1d.web.domain.news.article

import kotlin.time.Instant

data class ArticleView(
    val id: Int,
    val title: String,
    val previewImage: String? = null,
    val dateTime: Instant,
    val categories: List<Int>
)
