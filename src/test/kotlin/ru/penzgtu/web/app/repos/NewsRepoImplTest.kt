package ru.penzgtu.web.app.repos

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import ru.penzgtu.web.app.data.news.Article
import ru.penzgtu.web.app.data.news.ArticleView
import ru.penzgtu.web.app.data.news.categories.Category

class NewsRepoImplTest : NewsRepo {

    @OptIn(ExperimentalSerializationApi::class)
    override suspend fun list(categoryId: Int?, offset: Int, limit: Int): List<ArticleView> {
        val inputStream = this.javaClass.getResourceAsStream("/news_list.json")!!
        return Json.decodeFromStream(inputStream) ?: emptyList()
    }

    override suspend fun article(id: Int): Article {
        TODO("Not yet implemented")
    }

    override suspend fun listCategories(parentId: Int?): List<Category> {
        TODO("Not yet implemented")
    }
}