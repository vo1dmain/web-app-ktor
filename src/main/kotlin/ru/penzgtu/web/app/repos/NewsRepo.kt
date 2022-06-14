package ru.penzgtu.web.app.repos

import ru.penzgtu.web.app.data.news.Article
import ru.penzgtu.web.app.data.news.ArticleView
import ru.penzgtu.web.app.data.news.categories.Category

interface NewsRepo {
    suspend fun list(
        categoryId: Int? = null,
        offset: Int = defaultOffset,
        limit: Int = defaultLimit
    ): List<ArticleView>

    suspend fun article(id: Int): Article

    suspend fun listCategories(parentId: Int?): List<Category>

    companion object {
        const val defaultOffset = 0
        const val defaultLimit = 10
    }
}