package ru.vo1d.web.server.dao

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.decodeFromStream
import ru.vo1d.web.data.dao.AllDao
import ru.vo1d.web.data.dao.CategoryDao
import ru.vo1d.web.data.extensions.open
import ru.vo1d.web.data.extensions.resource
import ru.vo1d.web.data.filters.news.CategoryFilters
import ru.vo1d.web.entities.news.category.Category
import ru.vo1d.web.server.clampedSubList

@OptIn(ExperimentalSerializationApi::class)
class CategoryDaoRes : CategoryDao, JsonDao, AllDao<Category> {
    private val file = resource("/data/categories.json")

    override suspend fun create(item: Category): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun create(vararg items: Category): Int {
        TODO("Not yet implemented")
    }

    override suspend fun read(id: Int) = all().firstOrNull { it.id == id }

    override suspend fun update(item: Category): Int {
        TODO("Not yet implemented")
    }

    override suspend fun delete(vararg items: Category): Int {
        TODO("Not yet implemented")
    }

    override suspend fun page(offset: Long, limit: Int) = all().clampedSubList(offset.toInt(), limit)

    override suspend fun filter(filters: CategoryFilters, offset: Long, limit: Int) = page(offset, limit)
        .filter { category ->
            filters.parentId?.let { it == category.parentId } ?: true
        }

    override suspend fun all() = file.open {
        json.decodeFromStream<List<Category>>(this)
    }
}