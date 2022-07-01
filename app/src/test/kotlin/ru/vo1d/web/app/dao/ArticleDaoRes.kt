package ru.vo1d.web.app.dao

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.decodeFromStream
import ru.vo1d.web.app.clampedSubList
import ru.vo1d.web.data.dao.ArticleDao
import ru.vo1d.web.data.extensions.open
import ru.vo1d.web.data.filters.news.ArticleFilters
import ru.vo1d.web.entities.news.article.ArticleModel
import ru.vo1d.web.entities.news.article.ArticleView


@OptIn(ExperimentalSerializationApi::class)
class ArticleDaoRes : ArticleDao, JsonDao, AllDaoTest<ArticleModel> {
    private val newsList = this.javaClass.getResource("/articles.json")!!

    override suspend fun create(item: ArticleModel): Int {
        TODO("Not yet implemented")
    }

    override suspend fun read(id: Int): ArticleModel? {
        return all().firstOrNull { it.id == id }
    }

    override suspend fun update(item: ArticleModel): Int {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: Int): Int {
        TODO("Not yet implemented")
    }

    override suspend fun list(offset: Long, limit: Int): List<ArticleView> {
        return all().clampedSubList(offset.toInt(), limit).map {
            ArticleView(
                it.id!!,
                it.title,
                it.previewImage,
                it.dateTime,
                it.categories
            )
        }
    }

    override suspend fun filter(filters: ArticleFilters, offset: Long, limit: Int): List<ArticleView> {
        val categoryId = filters.categoryId
        return list(offset, limit).filter { view ->
            categoryId?.let { view.categories.contains(it) } ?: true
        }
    }

    override suspend fun all(): List<ArticleModel> {
        return newsList.open {
            json.decodeFromStream(this)
        }
    }
}