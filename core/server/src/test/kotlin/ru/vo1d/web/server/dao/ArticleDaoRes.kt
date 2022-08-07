package ru.vo1d.web.server.dao

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.decodeFromStream
import ru.vo1d.web.data.dao.ArticleDao
import ru.vo1d.web.data.extensions.open
import ru.vo1d.web.data.extensions.resource
import ru.vo1d.web.data.filters.news.ArticleFilters
import ru.vo1d.web.entities.news.article.Article
import ru.vo1d.web.entities.news.article.ArticleView
import ru.vo1d.web.server.clampedSubList


@OptIn(ExperimentalSerializationApi::class)
class ArticleDaoRes : ArticleDao, JsonDao, AllDaoTest<Article> {
    private val file = resource("/data/articles.json")

    override suspend fun create(item: Article): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun create(vararg items: Article): Int {
        TODO("Not yet implemented")
    }

    override suspend fun read(id: Int) = all().firstOrNull { it.id == id }

    override suspend fun update(item: Article): Int {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: Int): Int {
        TODO("Not yet implemented")
    }

    override suspend fun list(offset: Long, limit: Int) = all()
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

    override suspend fun filter(filters: ArticleFilters, offset: Long, limit: Int) = list(offset, limit)
        .filter { view ->
            view.categories.any { filters.categories?.contains(it) ?: true }
        }

    override suspend fun all() = file.open {
        json.decodeFromStream<List<Article>>(this)
    }
}