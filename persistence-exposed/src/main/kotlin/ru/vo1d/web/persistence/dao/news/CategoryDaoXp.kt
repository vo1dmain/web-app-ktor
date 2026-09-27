package ru.vo1d.web.persistence.dao.news

import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.core.inList
import org.jetbrains.exposed.v1.jdbc.batchInsert
import org.jetbrains.exposed.v1.jdbc.deleteWhere
import org.jetbrains.exposed.v1.jdbc.insertAndGetId
import org.jetbrains.exposed.v1.jdbc.update
import ru.vo1d.web.domain.dao.CategoryDao
import ru.vo1d.web.domain.filters.news.CategoryFilters
import ru.vo1d.web.domain.news.category.Category
import ru.vo1d.web.persistence.context.DbContext
import ru.vo1d.web.persistence.dao.XpDao
import ru.vo1d.web.persistence.entities.news.Categories
import ru.vo1d.web.persistence.entities.news.CategoryEntity
import ru.vo1d.web.persistence.mappers.mapItem
import ru.vo1d.web.persistence.mappers.toDomain

class CategoryDaoXp(ctx: DbContext) : XpDao(ctx.news), CategoryDao {
    override suspend fun create(item: Category): Int = query {
        Categories.insertAndGetId { it.mapItem(item) }.value
    }

    override suspend fun create(vararg items: Category): Int = query {
        Categories.batchInsert(items.asIterable()) { mapItem(it) }.count()
    }

    override suspend fun read(id: Int): Category? = query {
        CategoryEntity.findById(id)?.toDomain()
    }

    override suspend fun update(item: Category): Int = query {
        Categories.update({ Categories.id eq item.id }) { it.mapItem(item) }
    }

    override suspend fun delete(vararg items: Category): Int = query {
        Categories.deleteWhere { Categories.id inList items.mapNotNull { it.id } }
    }

    override suspend fun page(offset: Long, limit: Int): List<Category> = query {
        CategoryEntity.all()
            .limit(limit)
            .offset(offset)
            .map(CategoryEntity::toDomain)
    }

    override suspend fun filter(filters: CategoryFilters, offset: Long, limit: Int): List<Category> {
        val parentId = filters.parentId
            ?: return page(offset, limit)

        return query {
            CategoryEntity.find { Categories.parentId eq parentId }
                .limit(limit)
                .offset(offset)
                .map(CategoryEntity::toDomain)
        }
    }
}
