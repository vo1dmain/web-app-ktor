package ru.vo1d.web.persistence.mappers

import ru.vo1d.web.domain.news.article.Article
import ru.vo1d.web.domain.news.article.ArticleView
import ru.vo1d.web.domain.news.category.Category
import ru.vo1d.web.persistence.entities.news.*

internal fun ArticleEntity.toDomain() = Article(
    id.value,
    title,
    body,
    preview,
    gallery?.split(","),
    dateTime,
    categories.map { it.id.value }.toList()
)

internal fun CategoryEntity.toDomain() = Category(id.value, title, parentId?.value)

internal fun ArticleViewEntity.toDomain() = ArticleView(
    id.value,
    title,
    preview,
    dateTime,
    categories.map { it.id.value }.toList()
)