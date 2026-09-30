package ru.vo1d.web.domain.news.article

import kotlin.time.Instant

data class Article(
    val id: Int,
    val title: String,
    val body: String,
    val previewImage: String? = null,
    val gallery: List<String>? = null,
    val dateTime: Instant,
    val categories: List<Int>
)
