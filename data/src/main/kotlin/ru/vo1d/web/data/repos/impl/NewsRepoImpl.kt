package ru.vo1d.web.data.repos.impl

import ru.vo1d.web.data.dao.ArticleDao
import ru.vo1d.web.data.dao.CategoryDao
import ru.vo1d.web.data.filters.news.ArticleFilters
import ru.vo1d.web.data.filters.news.CategoryFilters
import ru.vo1d.web.data.repos.NewsRepo
import ru.vo1d.web.entities.news.article.ArticleView
import ru.vo1d.web.entities.news.category.CategoryModel

abstract class NewsRepoImpl : NewsRepo {
    protected abstract val articleDao: ArticleDao
    protected abstract val categoryDao: CategoryDao

    override suspend fun articles(filters: ArticleFilters, page: Int?): List<ArticleView> {
        val offset = offset(page)

        if (filters.areEmpty()) return articleDao.list(offset, limit)
        return articleDao.filter(filters, offset, limit)
    }

    override suspend fun article(id: Int) = articleDao.read(id)

    override suspend fun categories(filters: CategoryFilters, page: Int?): List<CategoryModel> {
        val offset = offset(page)

        if (filters.areEmpty()) return categoryDao.list(offset, limit)
        return categoryDao.filter(filters, offset, limit)
    }

    override suspend fun category(id: Int) = categoryDao.read(id)
}