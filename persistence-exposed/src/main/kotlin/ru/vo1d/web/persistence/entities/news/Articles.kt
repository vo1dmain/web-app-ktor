package ru.vo1d.web.persistence.entities.news

import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass
import org.jetbrains.exposed.v1.datetime.CurrentTimestamp
import org.jetbrains.exposed.v1.datetime.timestamp

internal object Articles : IntIdTable() {
    val title = varchar("title", 64)
    val body = varchar("body", 1024)
    val preview = varchar("preview", 128).nullable()
    val gallery = varchar("gallery", 1024).nullable()
    val dateTime = timestamp("dateTime").defaultExpression(CurrentTimestamp)
}

internal class ArticleEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ArticleEntity>(Articles)

    val title by Articles.title
    val body by Articles.body
    val preview by Articles.preview
    val gallery by Articles.gallery
    val dateTime by Articles.dateTime
    val categories by CategoryEntity via ArticleCategories
}

internal class ArticleViewEntity(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<ArticleViewEntity>(Articles)

    val title by Articles.title
    val preview by Articles.preview
    val dateTime by Articles.dateTime
    val categories by CategoryEntity via ArticleCategories
}