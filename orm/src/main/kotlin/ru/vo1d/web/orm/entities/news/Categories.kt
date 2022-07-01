package ru.vo1d.web.orm.entities.news

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption.CASCADE
import ru.vo1d.web.entities.news.category.CategoryModel
import ru.vo1d.web.orm.entities.HasModel

object Categories : IntIdTable() {
    val title = varchar("title", 128)
    val parentId = optReference("parentId", id, CASCADE, CASCADE)
}

class Category(id: EntityID<Int>) : IntEntity(id), HasModel<CategoryModel> {
    companion object : IntEntityClass<Category>(Categories)

    val title by Categories.title
    val parentId by Categories.parentId

    override fun toModel() = CategoryModel(id.value, title, parentId?.value)
}