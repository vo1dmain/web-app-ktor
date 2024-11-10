package ru.vo1d.web.exposed.entities.news

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption.CASCADE

internal object Categories : IntIdTable() {
    val title = varchar("title", 64)
    val parentId = optReference("parentId", id, CASCADE, CASCADE)
}

internal class CategoryEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<CategoryEntity>(Categories)

    val title by Categories.title
    val parentId by Categories.parentId
}