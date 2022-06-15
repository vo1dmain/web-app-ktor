package ru.penzgtu.web.app.repos

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import ru.penzgtu.web.app.data.news.Article
import ru.penzgtu.web.app.data.news.ArticleView
import ru.penzgtu.web.app.data.news.categories.Category
import ru.penzgtu.web.app.extensions.open

@OptIn(ExperimentalSerializationApi::class)
class NewsRepoImplTest : NewsRepo {
    private val newsList = this.javaClass.getResource("/news_list.json")!!
    private val categories = this.javaClass.getResource("/categories.json")!!

    private val serializer = Json { ignoreUnknownKeys = true }

    override suspend fun list(categoryId: Int?, offset: Int, limit: Int): List<ArticleView> {
        return newsList.open {
            serializer.decodeFromStream<List<ArticleView>>(this)
                .filter { view ->
                    categoryId?.let { view.categories.contains(it) } ?: true
                }
        }
    }

    override suspend fun item(id: Int): Article {
        return newsList.open {
            serializer.decodeFromStream<List<Article>>(this)
                .first { it.id == id }
        }
    }

    override suspend fun categories(parentId: Int?): List<Category> {
        return categories.open {
            serializer.decodeFromStream<List<Category>>(this)
                .filter { category ->
                    parentId?.let { it == category.parentId } ?: true
                }
        }
    }
}