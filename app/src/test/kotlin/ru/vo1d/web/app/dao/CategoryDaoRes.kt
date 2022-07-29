package ru.vo1d.web.app.dao

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.decodeFromStream
import ru.vo1d.web.app.clampedSubList
import ru.vo1d.web.data.dao.CategoryDao
import ru.vo1d.web.data.extensions.open
import ru.vo1d.web.data.filters.news.CategoryFilters
import ru.vo1d.web.entities.news.category.CategoryModel
import ru.vo1d.web.orm.extensions.resource

@OptIn(ExperimentalSerializationApi::class)
class CategoryDaoRes : CategoryDao, JsonDao, AllDaoTest<CategoryModel> {
    private val file = resource("/data/categories.json")

    override suspend fun create(item: CategoryModel): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun create(vararg items: CategoryModel): Int {
        TODO("Not yet implemented")
    }

    override suspend fun read(id: Int) = all().firstOrNull { it.id == id }

    override suspend fun update(item: CategoryModel): Int {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: Int): Int {
        TODO("Not yet implemented")
    }

    override suspend fun list(offset: Long, limit: Int) = all().clampedSubList(offset.toInt(), limit)

    override suspend fun filter(filters: CategoryFilters, offset: Long, limit: Int) = list(offset, limit)
        .filter { category ->
            filters.parentId?.let { it == category.parentId } ?: true
        }

    override suspend fun all() = file.open {
        json.decodeFromStream<List<CategoryModel>>(this)
    }
}