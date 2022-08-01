package ru.vo1d.web.orm.entities.news

import kotlinx.datetime.TimeZone
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.kotlin.datetime.datetime
import ru.vo1d.web.entities.news.article.ArticleView
import ru.vo1d.web.orm.entities.HasModel
import ru.vo1d.web.entities.news.article.Article as ArticleModel

object Articles : IntIdTable() {
    val title = varchar("title", 64)
    val body = varchar("body", 1024)
    val preview = varchar("preview", 128).nullable()
    val gallery = varchar("gallery", 1024).nullable()
    val dateTime = datetime("dateTime").defaultExpression(CurrentDateTime)
    val timeZone = varchar("timeZone", 32).default(TimeZone.currentSystemDefault().id)
}

class ArticleEntity(id: EntityID<Int>) : IntEntity(id), HasModel<ArticleModel> {
    companion object : IntEntityClass<ArticleEntity>(Articles)

    val title by Articles.title
    val body by Articles.body
    val preview by Articles.preview
    val gallery by Articles.gallery
    val dateTime by Articles.dateTime
    val timeZone by Articles.timeZone
    val categories by CategoryEntity via ArticleCategories

    override fun toModel() = ArticleModel(
        id.value,
        title,
        body,
        preview,
        gallery?.split(","),
        dateTime,
        TimeZone.of(timeZone),
        categories.map { it.id.value }.toList()
    )
}

class ArticleViewEntity(id: EntityID<Int>): IntEntity(id), HasModel<ArticleView> {
    companion object : IntEntityClass<ArticleViewEntity>(Articles)

    val title by Articles.title
    val preview by Articles.preview
    val dateTime by Articles.dateTime
    val timeZone by Articles.timeZone
    val categories by CategoryEntity via ArticleCategories

    override fun toModel() = ArticleView(
        id.value,
        title,
        preview,
        dateTime,
        TimeZone.of(timeZone),
        categories.map { it.id.value }.toList()
    )
}