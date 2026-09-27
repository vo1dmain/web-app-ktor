package ru.vo1d.web.domain.repos

import org.kodein.di.DI
import org.kodein.di.instance
import ru.vo1d.web.domain.dao.ArticleDao
import ru.vo1d.web.domain.dao.ArticleViewDao
import ru.vo1d.web.domain.dao.CategoryDao
import ru.vo1d.web.domain.filters.news.ArticleFilters
import ru.vo1d.web.domain.filters.news.CategoryFilters
import ru.vo1d.web.domain.news.article.Article
import ru.vo1d.web.domain.news.article.ArticleView
import ru.vo1d.web.domain.news.category.Category

class NewsRepo(di: DI) : ListRepo {
    private val articleDao: ArticleDao by di.instance()
    private val articleViewDao: ArticleViewDao by di.instance()
    private val categoryDao: CategoryDao by di.instance()

    suspend fun articles(page: Int?, filters: ArticleFilters): List<ArticleView> {
        val offset = offset(page)

        return if (filters == ArticleFilters.Empty) articleViewDao.page(offset, limit)
        else articleViewDao.filter(filters, offset, limit)
    }

    suspend fun article(id: Int): Article? {
        return articleDao.read(id)
    }

    suspend fun categories(page: Int?, filters: CategoryFilters): List<Category> {
        val offset = offset(page)

        return if (filters == CategoryFilters.Empty) categoryDao.page(offset, limit)
        else categoryDao.filter(filters, offset, limit)
    }

    suspend fun category(id: Int): Category? {
        return categoryDao.read(id)
    }
}