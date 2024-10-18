package ru.vo1d.web.data.filters.news

data class ArticleFilters(
    val categories: List<Int>? = null
) {
    companion object {
        val Empty = ArticleFilters()
    }
}