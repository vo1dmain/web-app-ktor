package ru.penzgtu.web.app.exposed.orm.news

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import ru.penzgtu.web.app.exposed.orm.HasModel
import ru.penzgtu.web.app.exposed.orm.HasView
import ru.penzgtu.web.entities.news.article.ArticleModel
import ru.penzgtu.web.entities.news.article.ArticleView

object Articles : IntIdTable() {
    val title = varchar("title", 256)
    val body = varchar("body", 1024)
    val preview = varchar("preview", 128).nullable()
    val gallery = varchar("gallery", 1024).nullable()
    val dateTime = long("dateTime")
}

class Article(id: EntityID<Int>) : IntEntity(id), HasModel<ArticleModel>, HasView<ArticleView> {
    companion object : IntEntityClass<Article>(Articles)

    val title by Articles.title
    val body by Articles.body
    val preview by Articles.preview
    val gallery by Articles.gallery
    val dateTime by Articles.dateTime
    val categories by Category via ArticleCategories

    override fun model() = ArticleModel(
        id.value,
        title,
        body,
        preview,
        gallery?.split(","),
        dateTime,
        categories.map { it.id.value }.toList()
    )

    override fun toView() = ArticleView(
        id.value,
        title,
        preview,
        dateTime,
        categories.map { it.id.value }.toList()
    )
}