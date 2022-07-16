package ru.vo1d.web.data.filters.news

import ru.vo1d.web.data.filters.Filters
import ru.vo1d.web.data.filters.FiltersBuilder

class CategoryFilters private constructor(builder: Builder) : Filters<CategoryFilters>(CategoryFilters::class) {
    val parentId = builder.parentId

    class Builder : FiltersBuilder<CategoryFilters> {
        var parentId: Int? = null

        override fun build() = CategoryFilters(this)
    }
}