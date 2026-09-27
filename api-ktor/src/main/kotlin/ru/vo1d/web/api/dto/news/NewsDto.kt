package ru.vo1d.web.api.dto.news

import kotlinx.serialization.Serializable
import ru.vo1d.web.domain.news.article.Article
import ru.vo1d.web.domain.news.article.ArticleView
import ru.vo1d.web.domain.news.category.Category
import kotlin.time.Instant

@Serializable
data class ArticleResponse(
    val id: Int,
    val title: String,
    val body: String,
    val previewImage: String? = null,
    val gallery: List<String>? = null,
    val dateTime: Instant,
    val categories: List<Int>
)

@Serializable
data class ArticleSummaryResponse(
    val id: Int,
    val title: String,
    val previewImage: String? = null,
    val dateTime: Instant,
    val categories: List<Int>
)

@Serializable
data class CategoryResponse(
    val id: Int,
    val title: String,
    val parentId: Int?
)

fun Article.toResponse() = ArticleResponse(id, title, body, previewImage, gallery, dateTime, categories)

fun ArticleView.toResponse() = ArticleSummaryResponse(id, title, previewImage, dateTime, categories)

fun Category.toResponse() = CategoryResponse(id, title, parentId)
