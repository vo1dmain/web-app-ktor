package ru.vo1d.web.exposed.repos

import ru.vo1d.web.data.dao.delegates.crudDao
import ru.vo1d.web.data.filters.news.ArticleFilters
import ru.vo1d.web.data.filters.news.CategoryFilters
import ru.vo1d.web.data.repos.base.NewsRepoBase
import ru.vo1d.web.exposed.context.DbContext
import ru.vo1d.web.exposed.dao.news.ArticleDaoXp
import ru.vo1d.web.exposed.dao.news.CategoryDaoXp

class NewsRepoXp(override val dbContext: DbContext) : NewsRepoBase(), XpRepo {
    override val articleDao by crudDao<ArticleDaoXp>()
    override val categoryDao by crudDao<CategoryDaoXp>()

    override suspend fun articles(page: Int?, filters: ArticleFilters) =
        dbContext {
            query(newsDb) { super.articles(page, filters) }
        }

    override suspend fun article(id: Int) = dbContext {
        query(newsDb) { super.article(id) }
    }

    override suspend fun categories(page: Int?, filters: CategoryFilters) =
        dbContext {
            query(newsDb) { super.categories(page, filters) }
        }

    override suspend fun category(id: Int) = dbContext {
        query(newsDb) { super.category(id) }
    }
}