package ru.vo1d.web.orm.dao.news

import org.jetbrains.exposed.sql.*
import ru.vo1d.web.data.filters.news.ArticleFilters
import ru.vo1d.web.data.dao.ArticleDao
import ru.vo1d.web.orm.entities.news.Article
import ru.vo1d.web.orm.entities.news.ArticleCategories
import ru.vo1d.web.orm.entities.news.Articles
import ru.vo1d.web.entities.news.article.ArticleModel
import ru.vo1d.web.entities.news.article.ArticleView

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

    override suspend fun read(id: Int) = Article.findById(id)?.toModel()

    override suspend fun update(item: ArticleModel) = Articles.update({ Articles.id eq item.id }) {
        it[title] = item.title
        it[body] = item.body
        it[preview] = item.previewImage
        it[gallery] = item.gallery?.joinToString(",")
        it[dateTime] = item.dateTime
    }

    override suspend fun delete(id: Int) = Articles.deleteWhere { Articles.id eq id }

    override suspend fun list(offset: Long, limit: Int) = Article.all().limit(limit, offset).map(Article::toView)

    override suspend fun filter(filters: ArticleFilters, offset: Long, limit: Int): List<ArticleView> {
        if (filters.categoryId == null) return list(offset, limit)

        val query = Articles.innerJoin(ArticleCategories).slice(Articles.columns)
            .select { ArticleCategories.categoryId eq filters.categoryId }

        return Article.wrapRows(query).limit(limit, offset).map(Article::toView)
    }
}