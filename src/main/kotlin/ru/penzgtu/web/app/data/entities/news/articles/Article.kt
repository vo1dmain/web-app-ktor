package ru.penzgtu.web.app.data.entities.news.articles

import kotlinx.serialization.Serializable

@Serializable
data class Article(
    val id: Int?,
    val title: String,
    val body: String,
    val previewImage: String?,
    val gallery: List<String>?,
    val dateTime: Long,
    val categories: List<Int>
)
