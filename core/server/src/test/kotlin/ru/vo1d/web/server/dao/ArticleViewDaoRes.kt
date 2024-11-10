package ru.vo1d.web.server.dao

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.decodeFromStream
import ru.vo1d.web.data.dao.ArticleViewDao
import ru.vo1d.web.data.extensions.open
import ru.vo1d.web.data.extensions.resource
import ru.vo1d.web.data.filters.news.ArticleFilters
import ru.vo1d.web.entities.news.article.Article
import ru.vo1d.web.entities.news.article.ArticleView
import ru.vo1d.web.server.clampedSubList

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
                it.dateTime ?: Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
                it.timeZone ?: TimeZone.currentSystemDefault(),
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