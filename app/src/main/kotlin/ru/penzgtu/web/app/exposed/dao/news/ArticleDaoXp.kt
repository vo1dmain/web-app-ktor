package ru.penzgtu.web.app.exposed.dao.news

import org.jetbrains.exposed.sql.*
import ru.penzgtu.web.app.data.dao.ArticleDao
import ru.penzgtu.web.app.data.filters.news.ArticleFilters
import ru.penzgtu.web.app.exposed.orm.news.Article
import ru.penzgtu.web.app.exposed.orm.news.ArticleCategories
import ru.penzgtu.web.app.exposed.orm.news.Articles
import ru.penzgtu.web.entities.news.articles.ArticleModel
import ru.penzgtu.web.entities.news.articles.ArticleView

class ArticleDaoXp : ArticleDao {
    override suspend fun create(item: ArticleModel): Int {
        val articleId = Articles.insertAndGetId {
            it[title] = item.title
            it[body] = item.body
            it[preview] = item.previewImage
            it[gallery] = item.gallery?.joinToString(",")
            it[dateTime] = item.dateTime
        }.value

        ArticleCategories.batchInsert(item.categories, shouldReturnGeneratedValues = false) {
            this[ArticleCategories.articleId] = articleId
            this[ArticleCategories.categoryId] = it
        }

        return articleId
    }

    override suspend fun read(id: Int): ArticleModel? {
        return Article.findById(id)?.model()
    }

    override suspend fun update(item: ArticleModel): Int {
        return Articles.update({ Articles.id eq item.id }) {
            it[title] = item.title
            it[body] = item.body
            it[preview] = item.previewImage
            it[gallery] = item.gallery?.joinToString(",")
            it[dateTime] = item.dateTime
        }
    }

    override suspend fun delete(id: Int): Int {
        return Articles.deleteWhere {
            Articles.id eq id
        }
    }

    override suspend fun list(offset: Long, limit: Int): List<ArticleView> {
        return Article.all().limit(limit, offset).map(Article::toView)
    }

    override suspend fun filter(filters: ArticleFilters, offset: Long, limit: Int): List<ArticleView> {
        if (filters.categoryId == null) return list(offset, limit)

        val query = Articles.innerJoin(ArticleCategories).slice(Articles.columns)
            .select { ArticleCategories.categoryId eq filters.categoryId }

        return Article.wrapRows(query).limit(limit, offset).map(Article::toView)
    }
}