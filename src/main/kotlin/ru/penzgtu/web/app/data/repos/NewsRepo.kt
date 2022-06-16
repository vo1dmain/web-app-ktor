package ru.penzgtu.web.app.data.repos

import ru.penzgtu.web.app.data.dao.ArticleDao
import ru.penzgtu.web.app.data.dao.CategoryDao
import ru.penzgtu.web.app.data.entities.news.Article
import ru.penzgtu.web.app.data.entities.news.ArticleView
import ru.penzgtu.web.app.data.entities.news.categories.Category
import ru.penzgtu.web.app.data.util.Filters

abstract class NewsRepo : ListRepo {
    protected abstract val articleDao: ArticleDao
    protected abstract val categoryDao: CategoryDao

    suspend fun articles(filters: Filters?, page: Int?): List<ArticleView> {
        val offset = offset(page)
        return filters?.let { articleDao.filter(it, offset, limit) } ?: articleDao.list(offset, limit)
    }

    suspend fun article(id: Int): Article? {
        return articleDao.read(id)
    }

    suspend fun categories(filters: Filters?, page: Int?): List<Category> {
        val offset = offset(page)
        return filters?.let { categoryDao.filter(it, offset, limit) } ?: categoryDao.list(offset, limit)
    }

    suspend fun category(id: Int): Category? {
        return categoryDao.read(id)
    }
}