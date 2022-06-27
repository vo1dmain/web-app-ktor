package ru.vo1d.web.app.data.filters.news

import ru.vo1d.web.app.data.dao.Filters
import ru.vo1d.web.app.data.dao.FiltersBuilder

interface ArticleFilters : Filters {
    val categoryId: Int?

    override fun areEmpty(): Boolean {
        return categoryId == null
    }

    companion object : FiltersBuilder<ArticleFilters> {
        private var categoryId: Int? = null

        fun categoryId(id: Int?) {
            categoryId = id
        }

        override fun build(): ArticleFilters {
            return object : ArticleFilters {
                override val categoryId = this@Companion.categoryId
            }
        }
    }
}


fun articleFilters(block: ArticleFilters.Companion.() -> Unit): ArticleFilters {
    return ArticleFilters.apply(block).build()
}
