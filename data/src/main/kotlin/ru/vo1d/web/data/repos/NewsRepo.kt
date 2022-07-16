package ru.vo1d.web.data.repos

import ru.vo1d.web.data.filters.news.ArticleFilters
import ru.vo1d.web.data.filters.news.CategoryFilters
import ru.vo1d.web.entities.news.article.ArticleModel
import ru.vo1d.web.entities.news.article.ArticleView
import ru.vo1d.web.entities.news.category.CategoryModel

interface NewsRepo : ListRepo {
    suspend fun articles(
        page: Int?,
        filtersBuilder: ArticleFilters.Builder.() -> Unit
    ): List<ArticleView>

    suspend fun article(id: Int): ArticleModel?

    suspend fun categories(
        page: Int?,
        filtersBuilder: CategoryFilters.Builder.() -> Unit
    ): List<CategoryModel>

    suspend fun category(id: Int): CategoryModel?
}