package ru.vo1d.web.exposed.dao.news

import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.core.inList
import org.jetbrains.exposed.v1.jdbc.batchInsert
import org.jetbrains.exposed.v1.jdbc.deleteWhere
import org.jetbrains.exposed.v1.jdbc.insertAndGetId
import org.jetbrains.exposed.v1.jdbc.update
import ru.vo1d.web.data.dao.ArticleDao
import ru.vo1d.web.entities.news.article.Article
import ru.vo1d.web.exposed.context.DbContext
import ru.vo1d.web.exposed.dao.XpDao
import ru.vo1d.web.exposed.entities.news.ArticleCategories
import ru.vo1d.web.exposed.entities.news.ArticleEntity
import ru.vo1d.web.exposed.entities.news.Articles
import ru.vo1d.web.exposed.mappers.mapItem
import ru.vo1d.web.exposed.mappers.toDomain

class ArticleDaoXp(ctx: DbContext) : XpDao(ctx.news), ArticleDao {
    override suspend fun create(item: Article): Int = query {
        insert(item)
    }

    override suspend fun create(vararg items: Article): Int = query {
        items.forEach { insert(it) }
        items.size
    }

    override suspend fun read(id: Int): Article? = query {
        ArticleEntity.findById(id)?.toDomain()
    }

    override suspend fun update(item: Article): Int = query {
        Articles.update({ Articles.id eq item.id }) { it.mapItem(item) }
    }

    override suspend fun delete(vararg items: Article): Int = query {
        Articles.deleteWhere { Articles.id inList items.mapNotNull { it.id } }
    }

    private fun insert(item: Article): Int {
        val articleId = Articles.insertAndGetId { it.mapItem(item) }.value

        ArticleCategories.batchInsert(item.categories, shouldReturnGeneratedValues = false) {
            this[ArticleCategories.articleId] = articleId
            this[ArticleCategories.categoryId] = it
        }

        return articleId
    }
}
