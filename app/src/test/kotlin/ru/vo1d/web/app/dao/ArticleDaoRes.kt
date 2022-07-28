package ru.vo1d.web.app.dao

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.decodeFromStream
import ru.vo1d.web.app.clampedSubList
import ru.vo1d.web.data.dao.ArticleDao
import ru.vo1d.web.data.extensions.open
import ru.vo1d.web.data.filters.news.ArticleFilters
import ru.vo1d.web.entities.news.article.ArticleModel
import ru.vo1d.web.entities.news.article.ArticleView
import ru.vo1d.web.orm.extensions.resource


@OptIn(ExperimentalSerializationApi::class)
class ArticleDaoRes : ArticleDao, JsonDao, AllDaoTest<ArticleModel> {
    private val file = resource("/articles.json")

    override suspend fun create(item: ArticleModel): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun create(vararg items: ArticleModel): Int {
        TODO("Not yet implemented")
    }

    override suspend fun read(id: Int) = all().firstOrNull { it.id == id }

    override suspend fun update(item: ArticleModel): Int {
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
            filters.categoryId?.let { view.categories.contains(it) } ?: true
        }

    override suspend fun all() = file.open {
        json.decodeFromStream<List<ArticleModel>>(this)
    }
}