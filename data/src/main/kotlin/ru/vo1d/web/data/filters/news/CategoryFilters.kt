package ru.vo1d.web.data.filters.news

import ru.vo1d.web.data.dao.filters.Filters
import ru.vo1d.web.data.dao.filters.FiltersBuilder
import ru.vo1d.web.data.dao.filters.FiltersCreator

interface CategoryFilters : Filters {
    companion object : FiltersCreator<CategoryFilters, Builder>(Builder::class)

    val parentId: Int?

    override fun areEmpty(): Boolean {
        return parentId == null
    }

    class Builder internal constructor() : FiltersBuilder<CategoryFilters> {
        var parentId: Int? = null

        override fun build() = object : CategoryFilters {
            override val parentId = this@Builder.parentId
        }
    }
}