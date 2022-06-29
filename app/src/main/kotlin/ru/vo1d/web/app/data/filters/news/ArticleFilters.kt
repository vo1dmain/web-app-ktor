package ru.vo1d.web.app.data.filters.news

import ru.vo1d.web.app.data.dao.filters.Filters
import ru.vo1d.web.app.data.dao.filters.FiltersBuilder
import ru.vo1d.web.app.data.dao.filters.FiltersCreator

interface ArticleFilters : Filters {
    companion object : FiltersCreator<ArticleFilters, Builder>(Builder::class)

    val categoryId: Int?

    override fun areEmpty() = categoryId == null

    class Builder internal constructor() : FiltersBuilder<ArticleFilters> {
        var categoryId: Int? = null

        override fun build() = object : ArticleFilters {
            override val categoryId = this@Builder.categoryId
        }
    }
}
