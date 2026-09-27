package ru.vo1d.web.persistence.dao.news

import org.jetbrains.exposed.v1.core.inList
import org.jetbrains.exposed.v1.jdbc.select
import ru.vo1d.web.domain.dao.ArticleViewDao
import ru.vo1d.web.domain.filters.news.ArticleFilters
import ru.vo1d.web.domain.news.article.ArticleView
import ru.vo1d.web.persistence.context.DbContext
import ru.vo1d.web.persistence.dao.XpDao
import ru.vo1d.web.persistence.entities.news.ArticleCategories
import ru.vo1d.web.persistence.entities.news.ArticleViewEntity
import ru.vo1d.web.persistence.entities.news.Articles
import ru.vo1d.web.persistence.mappers.toDomain

class ArticleViewDaoXp(ctx: DbContext) : XpDao(ctx.news), ArticleViewDao {
    override suspend fun page(offset: Long, limit: Int): List<ArticleView> = query {
        ArticleViewEntity.all()
            .limit(limit)
            .offset(offset)
            .map(ArticleViewEntity::toDomain)
    }

    override suspend fun filter(filters: ArticleFilters, offset: Long, limit: Int): List<ArticleView> {
        val categories = filters.categories
            ?: return page(offset, limit)

        return query {
            val query = Articles.innerJoin(ArticleCategories)
                .select(Articles.columns)
                .adjustWhere { ArticleCategories.categoryId inList categories }
                .groupBy(Articles.id)
                .limit(limit)
                .offset(offset)

            ArticleViewEntity.wrapRows(query).map(ArticleViewEntity::toDomain)
        }
    }
}
