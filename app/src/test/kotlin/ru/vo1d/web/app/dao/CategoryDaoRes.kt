package ru.vo1d.web.app.dao

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.decodeFromStream
import ru.vo1d.web.app.clampedSubList
import ru.vo1d.web.app.data.dao.CategoryDao
import ru.vo1d.web.app.data.filters.news.CategoryFilters
import ru.vo1d.web.app.extensions.open
import ru.vo1d.web.entities.news.category.CategoryModel

@OptIn(ExperimentalSerializationApi::class)
class CategoryDaoRes : CategoryDao, JsonDao, AllDaoTest<CategoryModel> {
    private val categories = this.javaClass.getResource("/categories.json")!!

    override suspend fun create(item: CategoryModel): Int {
        TODO("Not yet implemented")
    }

    override suspend fun read(id: Int): CategoryModel? {
        return all().firstOrNull { it.id == id }
    }

    override suspend fun update(item: CategoryModel): Int {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: Int): Int {
        TODO("Not yet implemented")
    }

    override suspend fun list(offset: Long, limit: Int): List<CategoryModel> {
        return all().clampedSubList(offset.toInt(), limit)
    }

    override suspend fun filter(filters: CategoryFilters, offset: Long, limit: Int): List<CategoryModel> {
        val parentId = filters.parentId
        return list(offset, limit).filter { category ->
            parentId?.let { it == category.parentId } ?: true
        }
    }

    override suspend fun all(): List<CategoryModel> {
        return categories.open {
            json.decodeFromStream(this)
        }
    }
}