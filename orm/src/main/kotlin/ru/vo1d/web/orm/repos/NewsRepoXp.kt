package ru.vo1d.web.orm.repos

import ru.vo1d.web.data.dao.delegates.dao
import ru.vo1d.web.data.filters.news.ArticleFilters
import ru.vo1d.web.data.filters.news.CategoryFilters
import ru.vo1d.web.data.repos.impl.NewsRepoImpl
import ru.vo1d.web.orm.dao.news.ArticleDaoXp
import ru.vo1d.web.orm.dao.news.CategoryDaoXp
import ru.vo1d.web.orm.db.DbManager

class NewsRepoXp(override val dbManager: DbManager) : NewsRepoImpl(), XpRepo {
    override val articleDao by dao<ArticleDaoXp>()
    override val categoryDao by dao<CategoryDaoXp>()

    override suspend fun articles(page: Int?, filtersBuilder: ArticleFilters.Builder.() -> Unit) =
        dbManager {
            query(newsDb) { super.articles(page, filtersBuilder) }
        }

    override suspend fun article(id: Int) = dbManager {
        query(newsDb) { super.article(id) }
    }

    override suspend fun categories(page: Int?, filtersBuilder: CategoryFilters.Builder.() -> Unit) =
        dbManager {
            query(newsDb) { super.categories(page, filtersBuilder) }
        }

    override suspend fun category(id: Int) = dbManager {
        query(newsDb) { super.category(id) }
    }
}