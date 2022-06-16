package ru.penzgtu.web.app.data.entities.news.categories

import ru.penzgtu.web.app.data.dao.Filters
import ru.penzgtu.web.app.data.dao.FiltersBuilder

interface CategoryFilters : Filters {
    val parentId: Int?

    override fun areEmpty(): Boolean {
        return parentId == null
    }
}

class Builder: FiltersBuilder<CategoryFilters> {
    private var parentId: Int? = null

    fun parentId(id: Int?) {
        parentId = id
    }

    override fun build(): CategoryFilters {
        return object : CategoryFilters {
            override val parentId = this@Builder.parentId
        }
    }
}

fun categoryFilters(block: Builder.() -> Unit): CategoryFilters {
    return Builder().apply(block).build()
}