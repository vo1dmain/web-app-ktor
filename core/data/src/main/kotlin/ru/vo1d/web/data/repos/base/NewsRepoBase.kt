package ru.vo1d.web.data.repos.base

import ru.vo1d.web.data.dao.ArticleDao
import ru.vo1d.web.data.dao.CategoryDao
import ru.vo1d.web.data.filters.filters
import ru.vo1d.web.data.filters.news.ArticleFilters
import ru.vo1d.web.data.filters.news.CategoryFilters
import ru.vo1d.web.data.repos.NewsRepo
import ru.vo1d.web.entities.news.article.ArticleView
import ru.vo1d.web.entities.news.category.Category

abstract class NewsRepoBase : NewsRepo {
    protected abstract val articleDao: ArticleDao
    protected abstract val categoryDao: CategoryDao

    override suspend fun articles(
        page: Int?,
        filtersBuilder: ArticleFilters.Builder.() -> Unit
    ): List<ArticleView> {
        val offset = offset(page)

        val filters = filters(filtersBuilder)
        if (filters.areEmpty()) return articleDao.list(offset, limit)
        return articleDao.filter(filters, offset, limit)
    }

    override suspend fun article(id: Int) = articleDao.read(id)

    override suspend fun categories(
        page: Int?,
        filtersBuilder: CategoryFilters.Builder.() -> Unit
    ): List<Category> {
        val offset = offset(page)

        val filters = filters(filtersBuilder)
        if (filters.areEmpty()) return categoryDao.list(offset, limit)
        return categoryDao.filter(filters, offset, limit)
    }

    override suspend fun category(id: Int) = categoryDao.read(id)
}