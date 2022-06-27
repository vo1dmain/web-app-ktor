package ru.vo1d.web.entities.news.category

import kotlinx.serialization.Serializable

@Serializable
data class CategoryModel(
    val id: Int? = null,
    val title: String,
    val parentId: Int?
)
