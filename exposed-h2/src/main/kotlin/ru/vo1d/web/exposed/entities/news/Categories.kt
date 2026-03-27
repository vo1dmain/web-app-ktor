package ru.vo1d.web.exposed.entities.news

import org.jetbrains.exposed.v1.core.ReferenceOption.CASCADE
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass

internal object Categories : IntIdTable() {
    val title = varchar("title", 64)
    val parentId = optReference("parentId", id, CASCADE, CASCADE)
}

internal class CategoryEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<CategoryEntity>(Categories)

    val title by Categories.title
    val parentId by Categories.parentId
}