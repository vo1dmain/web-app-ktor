package ru.vo1d.web.persistence.news

import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.andWhere
import org.jetbrains.exposed.v1.jdbc.selectAll
import ru.vo1d.web.domain.PageRequest
import ru.vo1d.web.domain.news.CategoryFilters
import ru.vo1d.web.domain.news.CategoryRepository
import ru.vo1d.web.domain.news.category.Category
import ru.vo1d.web.persistence.XpRepository
import ru.vo1d.web.persistence.context.DbContext
import ru.vo1d.web.persistence.entities.news.Categories
import ru.vo1d.web.persistence.entities.news.CategoryEntity
import ru.vo1d.web.persistence.mappers.toDomain

class CategoryRepositoryXp(ctx: DbContext) : XpRepository(ctx.news), CategoryRepository {
    override suspend fun find(filters: CategoryFilters, page: PageRequest): List<Category> = query {
        val query = Categories.selectAll().apply {
            filters.parentId?.let { andWhere { Categories.parentId eq it } }
            orderBy(Categories.id)
            limit(page.size)
            offset(page.offset)
        }

        CategoryEntity.wrapRows(query).map(CategoryEntity::toDomain)
    }

    override suspend fun get(id: Int): Category? = query {
        CategoryEntity.findById(id)?.toDomain()
    }
}
