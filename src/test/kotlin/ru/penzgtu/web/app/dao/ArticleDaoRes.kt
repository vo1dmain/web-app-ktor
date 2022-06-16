package ru.penzgtu.web.app.dao

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.decodeFromStream
import ru.penzgtu.web.app.clampedSubList
import ru.penzgtu.web.app.data.dao.ArticleDao
import ru.penzgtu.web.app.data.entities.news.articles.Article
import ru.penzgtu.web.app.data.entities.news.articles.ArticleFilters
import ru.penzgtu.web.app.data.entities.news.articles.ArticleView
import ru.penzgtu.web.app.extensions.open


@OptIn(ExperimentalSerializationApi::class)
class ArticleDaoRes : ArticleDao, JsonDao, AllDaoTest<Article> {
    private val newsList = this.javaClass.getResource("/news_list.json")!!

    override suspend fun create(item: Article): Int {
        TODO("Not yet implemented")
    }

    override suspend fun read(id: Int): Article? {
        return all().firstOrNull { it.id == id }
    }

    override suspend fun update(item: Article) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun list(offset: Int, limit: Int): List<ArticleView> {
        return all().clampedSubList(offset, limit).map {
            ArticleView(
                it.id!!,
                it.title,
                it.previewImage,
                it.dateTime,
                it.categories
            )
        }
    }

    override suspend fun filter(filters: ArticleFilters, offset: Int, limit: Int): List<ArticleView> {
        val categoryId = filters.categoryId
        return list(offset, limit).filter { view ->
            categoryId?.let { view.categories.contains(it) } ?: true
        }
    }

    override suspend fun all(): List<Article> {
        return newsList.open {
            json.decodeFromStream(this)
        }
    }
}