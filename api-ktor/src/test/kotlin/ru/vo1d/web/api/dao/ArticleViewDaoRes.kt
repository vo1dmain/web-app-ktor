package ru.vo1d.web.api.dao

import kotlin.time.Clock
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.decodeFromStream
import ru.vo1d.web.domain.dao.ArticleViewDao
import ru.vo1d.web.domain.extensions.open
import ru.vo1d.web.domain.extensions.resource
import ru.vo1d.web.domain.filters.news.ArticleFilters
import ru.vo1d.web.domain.news.article.Article
import ru.vo1d.web.domain.news.article.ArticleView
import ru.vo1d.web.api.clampedSubList

@OptIn(ExperimentalSerializationApi::class)
class ArticleViewDaoRes : ArticleViewDao, JsonDao {
    private val file = resource("/data/articles.json")

    override suspend fun page(offset: Long, limit: Int) = all()
        .clampedSubList(offset.toInt(), limit)
        .map {
            ArticleView(
                it.id!!,
                it.title,
                it.previewImage,
                it.dateTime ?: Clock.System.now(),
                it.categories
            )
        }

    override suspend fun filter(filters: ArticleFilters, offset: Long, limit: Int) = page(offset, limit)
        .filter { view ->
            view.categories.any { filters.categories?.contains(it) ?: true }
        }

    private suspend fun all() = file.open {
        json.decodeFromStream<List<Article>>(this)
    }
}