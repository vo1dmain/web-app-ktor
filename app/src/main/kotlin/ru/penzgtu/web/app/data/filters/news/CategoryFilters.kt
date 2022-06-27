package ru.penzgtu.web.app.data.filters.news

import ru.penzgtu.web.app.data.dao.Filters
import ru.penzgtu.web.app.data.dao.FiltersBuilder

interface CategoryFilters : Filters {
    val parentId: Int?

    override fun areEmpty(): Boolean {
        return parentId == null
    }

    companion object : FiltersBuilder<CategoryFilters> {
        private var parentId: Int? = null

        fun parentId(id: Int?) {
            parentId = id
        }

        override fun build(): CategoryFilters {
            return object : CategoryFilters {
                override val parentId = this@Companion.parentId
            }
        }
    }
}


fun categoryFilters(block: CategoryFilters.Companion.() -> Unit): CategoryFilters {
    return CategoryFilters.apply(block).build()
}