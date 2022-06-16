package ru.penzgtu.web.app.dao

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import ru.penzgtu.web.app.data.dao.ArticleDao
import ru.penzgtu.web.app.data.entities.news.Article
import ru.penzgtu.web.app.data.entities.news.ArticleView
import ru.penzgtu.web.app.data.util.FilterParams
import ru.penzgtu.web.app.extensions.open


@OptIn(ExperimentalSerializationApi::class)
class ArticleDaoResourceImpl : ArticleDao {
    private val newsList = this.javaClass.getResource("/news_list.json")!!

    private val json = Json { ignoreUnknownKeys = true }

    override suspend fun create(item: Article): Int {
        TODO("Not yet implemented")
    }

    override suspend fun read(id: Int): Article? {
        return newsList.open {
            json.decodeFromStream<List<Article>>(this)
                .firstOrNull { it.id == id }
        }
    }

    override suspend fun update(item: Article) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun list(offset: Int, limit: Int): List<ArticleView> {
        return newsList.open {
            json.decodeFromStream(this)
        }
    }

    override suspend fun filter(params: FilterParams, offset: Int, limit: Int): List<ArticleView> {
        val categoryId = params.int("category_id")
        return newsList.open {
            json.decodeFromStream<List<ArticleView>>(this).filter { view ->
                categoryId?.let { view.categories.contains(it) } ?: true
            }
        }
    }
}