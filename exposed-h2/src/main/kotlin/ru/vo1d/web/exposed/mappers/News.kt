package ru.vo1d.web.exposed.mappers

import org.jetbrains.exposed.v1.core.statements.UpdateBuilder
import ru.vo1d.web.entities.news.article.Article
import ru.vo1d.web.entities.news.article.ArticleView
import ru.vo1d.web.entities.news.category.Category
import ru.vo1d.web.exposed.entities.news.*

internal fun UpdateBuilder<*>.mapItem(item: Article) {
    this[Articles.title] = item.title
    this[Articles.body] = item.body
    this[Articles.preview] = item.previewImage
    this[Articles.gallery] = item.gallery?.joinToString(",")
    item.dateTime?.let { this[Articles.dateTime] = it }
}

internal fun UpdateBuilder<*>.mapItem(item: Category) {
    this[Categories.title] = item.title
    this[Categories.parentId] = item.parentId
}


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