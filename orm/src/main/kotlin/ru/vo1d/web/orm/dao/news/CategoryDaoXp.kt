package ru.vo1d.web.orm.dao.news

import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.update
import ru.vo1d.web.data.dao.CategoryDao
import ru.vo1d.web.data.filters.news.CategoryFilters
import ru.vo1d.web.entities.news.category.CategoryModel
import ru.vo1d.web.orm.dao.XpDao
import ru.vo1d.web.orm.entities.news.Categories
import ru.vo1d.web.orm.entities.news.Category

class CategoryDaoXp : CategoryDao, XpDao<CategoryModel> {
    override suspend fun create(item: CategoryModel) = Categories.insertAndGetId { it.mapItem(item) }.value

    override suspend fun create(vararg items: CategoryModel) =
        Categories.batchInsert(items.asIterable()) { mapItem(it) }.count()

    override suspend fun read(id: Int) = Category.findById(id)?.toModel()

    override suspend fun update(item: CategoryModel) =
        Categories.update({ Categories.id eq item.id }) { it.mapItem(item) }

    override suspend fun delete(id: Int) = Categories.deleteWhere { Categories.id eq id }

    override suspend fun list(offset: Long, limit: Int) = Category.all().limit(limit, offset).map(Category::toModel)

    override suspend fun filter(filters: CategoryFilters, offset: Long, limit: Int): List<CategoryModel> {
        if (filters.parentId == null) return list(offset, limit)

        return Category.find { Categories.parentId eq filters.parentId }.limit(limit, offset).map(Category::toModel)
    }

    override fun UpdateBuilder<*>.mapItem(item: CategoryModel) {
        this[Categories.title] = item.title
        this[Categories.parentId] = item.parentId
    }
}