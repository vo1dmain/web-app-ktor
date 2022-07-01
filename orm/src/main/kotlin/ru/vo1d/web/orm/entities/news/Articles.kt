package ru.vo1d.web.orm.entities.news

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import ru.vo1d.web.entities.news.article.ArticleModel
import ru.vo1d.web.entities.news.article.ArticleView
import ru.vo1d.web.orm.entities.HasModel
import ru.vo1d.web.orm.entities.HasView

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

    override fun toModel() = ArticleModel(
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