package ru.penzgtu.web.app.data.repos

import ru.penzgtu.web.app.data.dao.ArticleDao
import ru.penzgtu.web.app.data.dao.CategoryDao
import ru.penzgtu.web.app.data.entities.news.articles.Article
import ru.penzgtu.web.app.data.entities.news.articles.ArticleFilters
import ru.penzgtu.web.app.data.entities.news.articles.ArticleView
import ru.penzgtu.web.app.data.entities.news.categories.Category
import ru.penzgtu.web.app.data.entities.news.categories.CategoryFilters

abstract class NewsRepo : ListRepo {
    protected abstract val articleDao: ArticleDao
    protected abstract val categoryDao: CategoryDao

    suspend fun articles(filters: ArticleFilters, page: Int?): List<ArticleView> {
        val offset = offset(page)

        if (filters.areEmpty()) return articleDao.list(offset, limit)
        return articleDao.filter(filters, offset, limit)
    }

    suspend fun article(id: Int): Article? {
        return articleDao.read(id)
    }

    suspend fun categories(filters: CategoryFilters, page: Int?): List<Category> {
        val offset = offset(page)

        if (filters.areEmpty()) return categoryDao.list(offset, limit)
        return categoryDao.filter(filters, offset, limit)
    }

    suspend fun category(id: Int): Category? {
        return categoryDao.read(id)
    }
}