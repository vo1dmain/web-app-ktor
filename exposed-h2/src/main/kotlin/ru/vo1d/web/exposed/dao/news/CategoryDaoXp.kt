package ru.vo1d.web.exposed.dao.news

import org.jetbrains.exposed.sql.SqlExpressionBuilder.inList
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.update
import ru.vo1d.web.data.dao.CategoryDao
import ru.vo1d.web.data.filters.news.CategoryFilters
import ru.vo1d.web.entities.news.category.Category
import ru.vo1d.web.exposed.entities.news.Categories
import ru.vo1d.web.exposed.entities.news.CategoryEntity
import ru.vo1d.web.exposed.mappers.mapItem
import ru.vo1d.web.exposed.mappers.toDomain

class CategoryDaoXp : CategoryDao {
    override suspend fun create(item: Category): Int {
        return Categories.insertAndGetId { it.mapItem(item) }.value
    }

    override suspend fun create(vararg items: Category): Int {
        return Categories.batchInsert(items.asIterable()) { mapItem(it) }.count()
    }

    override suspend fun read(id: Int): Category? {
        return CategoryEntity.findById(id)?.toDomain()
    }

    override suspend fun update(item: Category): Int {
        return Categories.update({ Categories.id eq item.id }) { it.mapItem(item) }
    }

    override suspend fun delete(vararg items: Category): Int {
        return Categories.deleteWhere { Categories.id inList items.mapNotNull { it.id } }
    }

    override suspend fun page(offset: Long, limit: Int): List<Category> {
        return CategoryEntity.all()
            .limit(limit)
            .offset(offset)
            .map(CategoryEntity::toDomain)
    }

    override suspend fun filter(filters: CategoryFilters, offset: Long, limit: Int): List<Category> {
        if (filters.parentId == null)
            return page(offset, limit)

        return CategoryEntity.find { Categories.parentId eq filters.parentId }
            .limit(limit)
            .offset(offset)
            .map(CategoryEntity::toDomain)
    }
}