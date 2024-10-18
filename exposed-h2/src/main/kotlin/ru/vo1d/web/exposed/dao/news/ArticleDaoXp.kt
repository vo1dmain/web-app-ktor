package ru.vo1d.web.exposed.dao.news

import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.inList
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.update
import ru.vo1d.web.data.dao.ArticleDao
import ru.vo1d.web.data.filters.news.ArticleFilters
import ru.vo1d.web.entities.news.article.Article
import ru.vo1d.web.entities.news.article.ArticleView
import ru.vo1d.web.exposed.dao.XpDao
import ru.vo1d.web.exposed.entities.news.ArticleCategories
import ru.vo1d.web.exposed.entities.news.ArticleEntity
import ru.vo1d.web.exposed.entities.news.ArticleViewEntity
import ru.vo1d.web.exposed.entities.news.Articles

class ArticleDaoXp : ArticleDao, XpDao<Article> {
    override suspend fun create(item: Article): Int {
        val articleId = Articles.insertAndGetId { it.mapItem(item) }.value

        ArticleCategories.batchInsert(item.categories, shouldReturnGeneratedValues = false) {
            this[ArticleCategories.articleId] = articleId
            this[ArticleCategories.categoryId] = it
        }

        return articleId
    }

    override suspend fun create(vararg items: Article): Int {
        val inserted = mutableListOf<Int>()

        items.forEach {
            val id = create(it)
            inserted.add(id)
        }

        return inserted.count()
    }

    override suspend fun read(id: Int) =
        ArticleEntity.findById(id)?.toModel()

    override suspend fun update(item: Article) =
        Articles.update({ Articles.id eq item.id }) { it.mapItem(item) }

    override suspend fun delete(id: Int) =
        Articles.deleteWhere { Articles.id eq id }

    override suspend fun list(offset: Long, limit: Int) =
        ArticleViewEntity.all().limit(limit, offset).map(ArticleViewEntity::toModel)

    override suspend fun filter(filters: ArticleFilters, offset: Long, limit: Int): List<ArticleView> {
        if (filters.categories == null)
            return list(offset, limit)

        val query = Articles.innerJoin(ArticleCategories)
            .select(Articles.columns)
            .apply {
                filters.categories?.let {
                    adjustWhere { ArticleCategories.categoryId inList it }
                    groupBy(Articles.id)
                }
            }

        return ArticleViewEntity.wrapRows(query).limit(limit, offset).map(ArticleViewEntity::toModel)
    }

    override fun UpdateBuilder<*>.mapItem(item: Article) {
        this[Articles.title] = item.title
        this[Articles.body] = item.body
        this[Articles.preview] = item.previewImage
        this[Articles.gallery] = item.gallery?.joinToString(",")
        item.dateTime?.let { this[Articles.dateTime] = it }
        item.timeZone?.let { this[Articles.timeZone] = it.id }
    }
}