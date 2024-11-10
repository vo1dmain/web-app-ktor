package ru.vo1d.web.exposed.dao.news

import org.jetbrains.exposed.sql.SqlExpressionBuilder.inList
import ru.vo1d.web.data.dao.ArticleViewDao
import ru.vo1d.web.data.filters.news.ArticleFilters
import ru.vo1d.web.entities.news.article.ArticleView
import ru.vo1d.web.exposed.entities.news.ArticleCategories
import ru.vo1d.web.exposed.entities.news.ArticleViewEntity
import ru.vo1d.web.exposed.entities.news.Articles
import ru.vo1d.web.exposed.mappers.toDomain

class ArticleViewDaoXp : ArticleViewDao {
    override suspend fun page(offset: Long, limit: Int): List<ArticleView> {
        return ArticleViewEntity.all()
            .limit(limit)
            .offset(offset)
            .map(ArticleViewEntity::toDomain)
    }

    override suspend fun filter(filters: ArticleFilters, offset: Long, limit: Int): List<ArticleView> {
        val categories = filters.categories
            ?: return page(offset, limit)

        val query = Articles.innerJoin(ArticleCategories)
            .select(Articles.columns)
            .adjustWhere { ArticleCategories.categoryId inList categories }
            .groupBy(Articles.id)
            .limit(limit)
            .offset(offset)

        return ArticleViewEntity.wrapRows(query).map(ArticleViewEntity::toDomain)
    }
}