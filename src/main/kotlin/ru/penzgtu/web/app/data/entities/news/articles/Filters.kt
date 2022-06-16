package ru.penzgtu.web.app.data.entities.news.articles

import ru.penzgtu.web.app.data.dao.Filters
import ru.penzgtu.web.app.data.dao.FiltersBuilder

interface ArticleFilters: Filters {
    val categoryId: Int?

    override fun areEmpty(): Boolean {
        return categoryId == null
    }
}

class Builder: FiltersBuilder<ArticleFilters> {
    private var categoryId: Int? = null

    fun categoryId(id: Int?) {
        categoryId = id
    }

    override fun build(): ArticleFilters {
        return object : ArticleFilters {
            override val categoryId = this@Builder.categoryId
        }
    }
}

fun articleFilters(block: Builder.() -> Unit): ArticleFilters {
    return Builder().apply(block).build()
}
