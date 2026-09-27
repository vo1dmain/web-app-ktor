package ru.vo1d.web.api.fakes

import ru.vo1d.web.domain.PageRequest
import ru.vo1d.web.domain.news.ArticleFilters
import ru.vo1d.web.domain.news.ArticleRepository
import ru.vo1d.web.domain.news.article.Article
import ru.vo1d.web.domain.news.article.ArticleView
import kotlin.time.Clock

class FakeArticleRepository : ArticleRepository {
    override suspend fun find(filters: ArticleFilters, page: PageRequest) = all()
        .filter { article -> filters.categories?.let { article.categories.any(it::contains) } ?: true }
        .page(page)
        .map {
            ArticleView(
                it.id!!,
                it.title,
                it.previewImage,
                it.dateTime ?: Clock.System.now(),
                it.categories
            )
        }

    override suspend fun get(id: Int) = all().firstOrNull { it.id == id }

    private suspend fun all() = Fixtures.list("/data/articles.json", Article.serializer())
}
