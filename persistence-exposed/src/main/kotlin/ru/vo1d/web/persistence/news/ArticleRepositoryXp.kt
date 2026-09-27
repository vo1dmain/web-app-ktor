package ru.vo1d.web.persistence.news

import org.jetbrains.exposed.v1.core.inList
import org.jetbrains.exposed.v1.jdbc.select
import org.jetbrains.exposed.v1.jdbc.selectAll
import ru.vo1d.web.domain.PageRequest
import ru.vo1d.web.domain.news.ArticleFilters
import ru.vo1d.web.domain.news.ArticleRepository
import ru.vo1d.web.domain.news.article.Article
import ru.vo1d.web.domain.news.article.ArticleView
import ru.vo1d.web.persistence.XpRepository
import ru.vo1d.web.persistence.context.DbContext
import ru.vo1d.web.persistence.entities.news.ArticleCategories
import ru.vo1d.web.persistence.entities.news.ArticleEntity
import ru.vo1d.web.persistence.entities.news.ArticleViewEntity
import ru.vo1d.web.persistence.entities.news.Articles
import ru.vo1d.web.persistence.mappers.toDomain

class ArticleRepositoryXp(ctx: DbContext) : XpRepository(ctx.news), ArticleRepository {
    override suspend fun find(filters: ArticleFilters, page: PageRequest): List<ArticleView> = query {
        val query = filters.categories
            ?.let { categories ->
                Articles.innerJoin(ArticleCategories)
                    .select(Articles.columns)
                    .where { ArticleCategories.categoryId inList categories }
                    .groupBy(Articles.id)
            }
            ?: Articles.selectAll()

        val paged = query.orderBy(Articles.id)
            .limit(page.size)
            .offset(page.offset)

        ArticleViewEntity.wrapRows(paged).map(ArticleViewEntity::toDomain)
    }

    override suspend fun get(id: Int): Article? = query {
        ArticleEntity.findById(id)?.toDomain()
    }
}
