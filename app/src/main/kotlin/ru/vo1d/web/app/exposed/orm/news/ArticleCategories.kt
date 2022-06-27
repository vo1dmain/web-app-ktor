package ru.vo1d.web.app.exposed.orm.news

import org.jetbrains.exposed.sql.ReferenceOption.CASCADE
import org.jetbrains.exposed.sql.Table

object ArticleCategories : Table() {
    val articleId = reference("articleId", Articles, CASCADE, CASCADE)
    val categoryId = reference("categoryId", Categories, CASCADE, CASCADE)

    override val primaryKey = PrimaryKey(
        articleId, categoryId
    )
}