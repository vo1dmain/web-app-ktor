package ru.penzgtu.web.app.data.repos

import ru.penzgtu.web.app.data.dao.ArticleDao
import ru.penzgtu.web.app.data.dao.CategoryDao
import ru.penzgtu.web.app.data.filters.news.ArticleFilters
import ru.penzgtu.web.app.data.filters.news.CategoryFilters
import ru.penzgtu.web.entities.news.article.ArticleModel
import ru.penzgtu.web.entities.news.article.ArticleView
import ru.penzgtu.web.entities.news.category.CategoryModel

abstract class NewsRepo : ListRepo {
    protected abstract val articleDao: ArticleDao
    protected abstract val categoryDao: CategoryDao

    suspend fun articles(filters: ArticleFilters, page: Int?): List<ArticleView> {
        val offset = offset(page)

        if (filters.areEmpty()) return articleDao.list(offset, limit)
        return articleDao.filter(filters, offset, limit)
    }

    suspend fun article(id: Int): ArticleModel? {
        return articleDao.read(id)
    }

    suspend fun categories(filters: CategoryFilters, page: Int?): List<CategoryModel> {
        val offset = offset(page)

        if (filters.areEmpty()) return categoryDao.list(offset, limit)
        return categoryDao.filter(filters, offset, limit)
    }

    suspend fun category(id: Int): CategoryModel? {
        return categoryDao.read(id)
    }
}