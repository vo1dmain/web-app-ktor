package ru.penzgtu.web.app.dao

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.decodeFromStream
import ru.penzgtu.web.app.clampedSubList
import ru.penzgtu.web.app.data.dao.CategoryDao
import ru.penzgtu.web.app.data.entities.news.categories.Category
import ru.penzgtu.web.app.data.util.Filters
import ru.penzgtu.web.app.extensions.open

@OptIn(ExperimentalSerializationApi::class)
class CategoryDaoRes : CategoryDao, JsonDao, AllDaoTest<Category> {
    private val categories = this.javaClass.getResource("/categories.json")!!

    override suspend fun create(item: Category): Int {
        TODO("Not yet implemented")
    }

    override suspend fun read(id: Int): Category? {
        return all().firstOrNull { it.id == id }
    }

    override suspend fun update(item: Category) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun list(offset: Int, limit: Int): List<Category> {
        return all().clampedSubList(offset, limit)
    }

    override suspend fun filter(filters: Filters, offset: Int, limit: Int): List<Category> {
        val parentId = filters.int("parent_id")
        return list(offset, limit).filter { category ->
            parentId?.let { it == category.parentId } ?: true
        }
    }

    override suspend fun all(): List<Category> {
        return categories.open {
            json.decodeFromStream(this)
        }
    }
}