package ru.vo1d.web.app.data.filters.news

import ru.vo1d.web.app.data.dao.filters.Filters
import ru.vo1d.web.app.data.dao.filters.FiltersBuilder

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