package ru.vo1d.web.domain.news

import ru.vo1d.web.domain.PageRequest
import ru.vo1d.web.domain.news.article.Article
import ru.vo1d.web.domain.news.article.ArticleView

interface ArticleRepository {
    suspend fun find(filters: ArticleFilters, page: PageRequest): List<ArticleView>

    suspend fun get(id: Int): Article?
}
