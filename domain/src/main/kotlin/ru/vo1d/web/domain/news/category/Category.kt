package ru.vo1d.web.domain.news.category

data class Category(
    val id: Int,
    val title: String,
    val parentId: Int?
)
