package ru.vo1d.web.orm.repos

import ru.vo1d.web.data.dao.delegates.dao
import ru.vo1d.web.data.filters.news.ArticleFilters
import ru.vo1d.web.data.filters.news.CategoryFilters
import ru.vo1d.web.data.repos.impl.NewsRepoImpl
import ru.vo1d.web.orm.dao.news.ArticleDaoXp
import ru.vo1d.web.orm.dao.news.CategoryDaoXp
import ru.vo1d.web.orm.db.DbManager

class NewsRepoXp(private val dbManager: DbManager) : NewsRepoImpl() {
    override val articleDao by dao<ArticleDaoXp>()
    override val categoryDao by dao<CategoryDaoXp>()

    override suspend fun articles(page: Int?, filtersProducer: ArticleFilters.() -> Unit) = with(dbManager) {
        query(newsDb) { super.articles(page, filtersProducer) }
    }

    override suspend fun article(id: Int) = with(dbManager) {
        query(newsDb) { super.article(id) }
    }

    override suspend fun categories(page: Int?, filtersProducer: CategoryFilters.() -> Unit) = with(dbManager) {
        query(newsDb) { super.categories(page, filtersProducer) }
    }

    override suspend fun category(id: Int) = with(dbManager) {
        query(newsDb) { super.category(id) }
    }
}