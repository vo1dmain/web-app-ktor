package ru.vo1d.web.exposed.repos

import ru.vo1d.web.data.dao.delegates.crudDao
import ru.vo1d.web.data.filters.news.ArticleFilters
import ru.vo1d.web.data.filters.news.CategoryFilters
import ru.vo1d.web.data.repos.impl.NewsRepoImpl
import ru.vo1d.web.exposed.dao.news.ArticleDaoXp
import ru.vo1d.web.exposed.dao.news.CategoryDaoXp
import ru.vo1d.web.exposed.context.DbContext

class NewsRepoXp(override val dbContext: DbContext) : NewsRepoImpl(), XpRepo {
    override val articleDao by crudDao<ArticleDaoXp>()
    override val categoryDao by crudDao<CategoryDaoXp>()

    override suspend fun articles(page: Int?, filtersBuilder: ArticleFilters.Builder.() -> Unit) =
        dbContext {
            query(newsDb) { super.articles(page, filtersBuilder) }
        }

    override suspend fun article(id: Int) = dbContext {
        query(newsDb) { super.article(id) }
    }

    override suspend fun categories(page: Int?, filtersBuilder: CategoryFilters.Builder.() -> Unit) =
        dbContext {
            query(newsDb) { super.categories(page, filtersBuilder) }
        }

    override suspend fun category(id: Int) = dbContext {
        query(newsDb) { super.category(id) }
    }
}