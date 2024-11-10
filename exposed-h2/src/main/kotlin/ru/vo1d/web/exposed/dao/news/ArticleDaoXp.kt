package ru.vo1d.web.exposed.dao.news

import org.jetbrains.exposed.sql.SqlExpressionBuilder.inList
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.update
import ru.vo1d.web.data.dao.ArticleDao
import ru.vo1d.web.entities.news.article.Article
import ru.vo1d.web.exposed.entities.news.ArticleCategories
import ru.vo1d.web.exposed.entities.news.ArticleEntity
import ru.vo1d.web.exposed.entities.news.Articles
import ru.vo1d.web.exposed.mappers.mapItem
import ru.vo1d.web.exposed.mappers.toDomain

class ArticleDaoXp : ArticleDao {
    override suspend fun create(item: Article): Int {
        val articleId = Articles.insertAndGetId { it.mapItem(item) }.value

        ArticleCategories.batchInsert(item.categories, shouldReturnGeneratedValues = false) {
            this[ArticleCategories.articleId] = articleId
            this[ArticleCategories.categoryId] = it
        }

        return articleId
    }

    override suspend fun create(vararg items: Article): Int {
        var inserted = 0

        items.forEach {
            create(it)
            ++inserted
        }

        return inserted
    }

    override suspend fun read(id: Int): Article? {
        return ArticleEntity.findById(id)?.toDomain()
    }

    override suspend fun update(item: Article): Int {
        return Articles.update({ Articles.id eq item.id }) { it.mapItem(item) }
    }

    override suspend fun delete(vararg items: Article): Int {
        return Articles.deleteWhere { Articles.id inList items.mapNotNull { it.id } }
    }
}