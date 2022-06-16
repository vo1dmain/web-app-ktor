package ru.penzgtu.web.app.dao

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import ru.penzgtu.web.app.data.dao.CategoryDao
import ru.penzgtu.web.app.data.entities.news.categories.Category
import ru.penzgtu.web.app.data.util.FilterParams
import ru.penzgtu.web.app.extensions.open

@OptIn(ExperimentalSerializationApi::class)
class CategoryDaoResourceImpl : CategoryDao {
    private val categories = this.javaClass.getResource("/categories.json")!!
    private val json = Json { ignoreUnknownKeys = true }

    override suspend fun create(item: Category): Int {
        TODO("Not yet implemented")
    }

    override suspend fun read(id: Int): Category? {
        return categories.open {
            json.decodeFromStream<List<Category>>(this)
                .firstOrNull { it.id == id }
        }
    }

    override suspend fun update(item: Category) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun list(offset: Int, limit: Int): List<Category> {
        return categories.open {
            json.decodeFromStream(this)
        }
    }

    override suspend fun filter(params: FilterParams, offset: Int, limit: Int): List<Category> {
        val parentId = params.int("parent_id")
        return categories.open {
            json.decodeFromStream<List<Category>>(this)
                .filter { category ->
                    parentId?.let { it == category.parentId } ?: true
                }
        }
    }
}