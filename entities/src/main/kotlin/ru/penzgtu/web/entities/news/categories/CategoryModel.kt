package ru.penzgtu.web.entities.news.categories

import kotlinx.serialization.Serializable

@Serializable
data class CategoryModel(
    val id: Int?,
    val title: String,
    val parentId: Int?
)
