package ru.penzgtu.web.app.exposed.repos

import ru.penzgtu.web.app.data.dao.delegates.dao
import ru.penzgtu.web.app.data.filters.news.ArticleFilters
import ru.penzgtu.web.app.data.filters.news.CategoryFilters
import ru.penzgtu.web.app.data.repos.NewsRepo
import ru.penzgtu.web.app.exposed.dao.news.ArticleDaoXp
import ru.penzgtu.web.app.exposed.dao.news.CategoryDaoXp
import ru.penzgtu.web.app.exposed.db.DbManager

class NewsRepoXp(private val dbManager: DbManager) : NewsRepo() {
    override val articleDao by dao<ArticleDaoXp>()
    override val categoryDao by dao<CategoryDaoXp>()

    override suspend fun articles(filters: ArticleFilters, page: Int?) = with(dbManager) {
        query(newsDb) { super.articles(filters, page) }
    }

    override suspend fun article(id: Int) = with(dbManager) {
        query(newsDb) { super.article(id) }
    }

    override suspend fun categories(filters: CategoryFilters, page: Int?) = with(dbManager) {
        query(newsDb) { super.categories(filters, page) }
    }

    override suspend fun category(id: Int) = with(dbManager) {
        query(newsDb) { super.category(id) }
    }
}