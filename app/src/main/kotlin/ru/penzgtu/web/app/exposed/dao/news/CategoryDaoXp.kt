package ru.penzgtu.web.app.exposed.dao.news

import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.update
import ru.penzgtu.web.app.data.dao.CategoryDao
import ru.penzgtu.web.app.data.filters.news.CategoryFilters
import ru.penzgtu.web.app.exposed.orm.news.Categories
import ru.penzgtu.web.app.exposed.orm.news.Category
import ru.penzgtu.web.entities.news.categories.CategoryModel

class CategoryDaoXp : CategoryDao {
    override suspend fun create(item: CategoryModel): Int {
        return Categories.insertAndGetId {
            it[title] = item.title
            it[parentId] = item.parentId
        }.value
    }

    override suspend fun read(id: Int): CategoryModel? {
        return Category.findById(id)?.model()
    }

    override suspend fun update(item: CategoryModel): Int {
        return Categories.update({ Categories.id eq item.id }) {
            it[title] = item.title
            it[parentId] = item.parentId
        }
    }

    override suspend fun delete(id: Int): Int {
        return Categories.deleteWhere {
            Categories.id eq id
        }
    }

    override suspend fun list(offset: Long, limit: Int): List<CategoryModel> {
        return Category.all().limit(limit, offset).map(Category::model)
    }

    override suspend fun filter(filters: CategoryFilters, offset: Long, limit: Int): List<CategoryModel> {
        if (filters.parentId == null) return list(offset, limit)

        return Category.find { Categories.parentId eq filters.parentId }
            .limit(limit, offset).map(Category::model)
    }
}