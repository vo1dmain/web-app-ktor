package ru.vo1d.web.exposed.dao.news

import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.update
import ru.vo1d.web.data.dao.CategoryDao
import ru.vo1d.web.data.filters.news.CategoryFilters
import ru.vo1d.web.entities.news.category.Category
import ru.vo1d.web.exposed.dao.XpDao
import ru.vo1d.web.exposed.entities.news.Categories
import ru.vo1d.web.exposed.entities.news.CategoryEntity

class CategoryDaoXp : CategoryDao, XpDao<Category> {
    override suspend fun create(item: Category) =
        Categories.insertAndGetId { it.mapItem(item) }.value

    override suspend fun create(vararg items: Category) =
        Categories.batchInsert(items.asIterable()) { mapItem(it) }.count()

    override suspend fun read(id: Int) =
        CategoryEntity.findById(id)?.toModel()

    override suspend fun update(item: Category) =
        Categories.update({ Categories.id eq item.id }) { it.mapItem(item) }

    override suspend fun delete(id: Int) =
        Categories.deleteWhere { Categories.id eq id }

    override suspend fun list(offset: Long, limit: Int) =
        CategoryEntity.all().limit(limit, offset).map(CategoryEntity::toModel)

    override suspend fun filter(filters: CategoryFilters, offset: Long, limit: Int): List<Category> {
        if (filters.parentId == null)
            return list(offset, limit)

        return CategoryEntity
            .find { Categories.parentId eq filters.parentId }
            .limit(limit, offset)
            .map(CategoryEntity::toModel)
    }

    override fun UpdateBuilder<*>.mapItem(item: Category) {
        this[Categories.title] = item.title
        this[Categories.parentId] = item.parentId
    }
}