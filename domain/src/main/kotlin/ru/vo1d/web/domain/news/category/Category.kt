package ru.vo1d.web.domain.news.category

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val id: Int? = null,
    val title: String,
    val parentId: Int?
)
