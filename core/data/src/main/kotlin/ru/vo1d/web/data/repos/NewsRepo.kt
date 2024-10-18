package ru.vo1d.web.data.repos

import ru.vo1d.web.data.filters.news.ArticleFilters
import ru.vo1d.web.data.filters.news.CategoryFilters
import ru.vo1d.web.entities.news.article.Article
import ru.vo1d.web.entities.news.article.ArticleView
import ru.vo1d.web.entities.news.category.Category

interface NewsRepo : ListRepo {
    suspend fun articles(page: Int?, filters: ArticleFilters): List<ArticleView>

    suspend fun article(id: Int): Article?

    suspend fun categories(page: Int?, filters: CategoryFilters): List<Category>

    suspend fun category(id: Int): Category?
}