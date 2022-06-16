package ru.penzgtu.web.app.data.entities.news

import kotlinx.serialization.Serializable

@Serializable
data class ArticleView(
    val id: Int,
    val title: String,
    val previewImage: String?,
    val dateTime: Long,
    val categories: List<Int>
)
