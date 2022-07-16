package ru.vo1d.web.data.filters.news

import ru.vo1d.web.data.filters.Filters

class ArticleFilters private constructor(builder: Builder) : Filters<ArticleFilters>(ArticleFilters::class) {
    val categoryId = builder.categoryId

    class Builder : Filters.Builder<ArticleFilters> {
        var categoryId: Int? = null

        override fun build() = ArticleFilters(this)
    }
}
