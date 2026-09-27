package ru.vo1d.web.persistence.news

import org.jetbrains.exposed.v1.core.ReferenceOption.CASCADE
import org.jetbrains.exposed.v1.core.Table

internal object ArticleCategories : Table() {
    val articleId = reference("articleId", Articles, CASCADE, CASCADE)
    val categoryId = reference("categoryId", Categories, CASCADE, CASCADE)

    override val primaryKey = PrimaryKey(articleId, categoryId)
}