package ru.vo1d.web.server.dao

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.decodeFromStream
import ru.vo1d.web.data.dao.AllDao
import ru.vo1d.web.data.dao.ArticleDao
import ru.vo1d.web.data.extensions.open
import ru.vo1d.web.data.extensions.resource
import ru.vo1d.web.entities.news.article.Article


@OptIn(ExperimentalSerializationApi::class)
class ArticleDaoRes : ArticleDao, JsonDao, AllDao<Article> {
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

    override suspend fun delete(vararg items: Article): Int {
        TODO("Not yet implemented")
    }

    override suspend fun all() = file.open {
        json.decodeFromStream<List<Article>>(this)
    }
}