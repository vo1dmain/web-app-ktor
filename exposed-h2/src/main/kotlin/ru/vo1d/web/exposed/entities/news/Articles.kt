package ru.vo1d.web.exposed.entities.news

import kotlinx.datetime.TimeZone
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

internal object Articles : IntIdTable() {
    val title = varchar("title", 64)
    val body = varchar("body", 1024)
    val preview = varchar("preview", 128).nullable()
    val gallery = varchar("gallery", 1024).nullable()
    val dateTime = datetime("dateTime").defaultExpression(CurrentDateTime)
    val timeZone = varchar("timeZone", 32).default(TimeZone.currentSystemDefault().id)
}

internal class ArticleEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ArticleEntity>(Articles)

    val title by Articles.title
    val body by Articles.body
    val preview by Articles.preview
    val gallery by Articles.gallery
    val dateTime by Articles.dateTime
    val timeZone by Articles.timeZone
    val categories by CategoryEntity via ArticleCategories
}

internal class ArticleViewEntity(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<ArticleViewEntity>(Articles)

    val title by Articles.title
    val preview by Articles.preview
    val dateTime by Articles.dateTime
    val timeZone by Articles.timeZone
    val categories by CategoryEntity via ArticleCategories
}