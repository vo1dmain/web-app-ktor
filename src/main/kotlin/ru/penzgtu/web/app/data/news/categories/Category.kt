package ru.penzgtu.web.app.data.news.categories

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val id: Int,
    val title: String,
    val parentId: Int?
)
