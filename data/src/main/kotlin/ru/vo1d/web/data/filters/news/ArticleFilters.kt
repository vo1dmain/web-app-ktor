package ru.vo1d.web.data.filters.news

import ru.vo1d.web.data.dao.filters.Filters
import ru.vo1d.web.data.dao.filters.FiltersBuilder

class ArticleFilters private constructor() : Filters<ArticleFilters>(ArticleFilters::class) {
    companion object : FiltersBuilder<ArticleFilters>(ArticleFilters::class)

    var categoryId: Int? = null
        private set

    fun categoryId(init: () -> Int?) {
        categoryId = init()
    }
}
