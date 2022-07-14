package ru.vo1d.web.data.filters.news

import ru.vo1d.web.data.dao.filters.Filters
import ru.vo1d.web.data.dao.filters.FiltersBuilder

class CategoryFilters private constructor(): Filters<CategoryFilters>(CategoryFilters::class) {
    companion object : FiltersBuilder<CategoryFilters>(CategoryFilters::class)

    var parentId: Int? = null
        private set

    fun parentId(init: () -> Int?) {
        parentId = init()
    }
}