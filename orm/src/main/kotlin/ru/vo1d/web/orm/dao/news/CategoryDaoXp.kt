package ru.vo1d.web.orm.dao.news

import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.update
import ru.vo1d.web.data.filters.news.CategoryFilters
import ru.vo1d.web.data.dao.CategoryDao
import ru.vo1d.web.orm.entities.news.Categories
import ru.vo1d.web.orm.entities.news.Category
import ru.vo1d.web.entities.news.category.CategoryModel

class CategoryDaoXp : CategoryDao {
    override suspend fun create(item: CategoryModel) = Categories.insertAndGetId {
        it[title] = item.title
        it[parentId] = item.parentId
    }.value

    override suspend fun read(id: Int) = Category.findById(id)?.toModel()

    override suspend fun update(item: CategoryModel) = Categories.update({ Categories.id eq item.id }) {
        it[title] = item.title
        it[parentId] = item.parentId
    }

    override suspend fun delete(id: Int) = Categories.deleteWhere { Categories.id eq id }

    override suspend fun list(offset: Long, limit: Int) = Category.all().limit(limit, offset).map(Category::toModel)

    override suspend fun filter(filters: CategoryFilters, offset: Long, limit: Int): List<CategoryModel> {
        if (filters.parentId == null) return list(offset, limit)

        return Category.find { Categories.parentId eq filters.parentId }.limit(limit, offset).map(Category::toModel)
    }
}