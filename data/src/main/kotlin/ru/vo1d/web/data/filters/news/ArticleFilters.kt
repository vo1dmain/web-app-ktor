package ru.vo1d.web.data.filters.news

import ru.vo1d.web.data.filters.Filters
import ru.vo1d.web.data.filters.FiltersBuilder

class ArticleFilters private constructor(builder: Builder) : Filters<ArticleFilters>(ArticleFilters::class) {
    val categoryId = builder.categoryId

    class Builder : FiltersBuilder<ArticleFilters> {
        var categoryId: Int? = null
        override fun build() = ArticleFilters(this)
    }
}
