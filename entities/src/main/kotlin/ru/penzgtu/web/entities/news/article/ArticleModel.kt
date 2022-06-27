package ru.penzgtu.web.entities.news.article

import kotlinx.serialization.Serializable

@Serializable
data class ArticleModel(
    val id: Int? = null,
    val title: String,
    val body: String,
    val previewImage: String?,
    val gallery: List<String>?,
    val dateTime: Long,
    val categories: List<Int>
)
