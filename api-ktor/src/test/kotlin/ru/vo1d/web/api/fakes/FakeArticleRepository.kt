package ru.vo1d.web.api.fakes

import ru.vo1d.web.domain.PageRequest
import ru.vo1d.web.domain.news.ArticleFilters
import ru.vo1d.web.domain.news.ArticleRepository
import ru.vo1d.web.domain.news.article.ArticleView

class FakeArticleRepository : ArticleRepository {
    private val articles = TestData.articles

    override suspend fun find(filters: ArticleFilters, page: PageRequest) = articles
        .filter { article -> filters.categories?.let { article.categories.any(it::contains) } ?: true }
        .page(page)
        .map { ArticleView(it.id, it.title, it.previewImage, it.dateTime, it.categories) }

    override suspend fun get(id: Int) = articles.firstOrNull { it.id == id }
}
