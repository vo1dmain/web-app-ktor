package ru.vo1d.web.data.filters.news

data class CategoryFilters(
    val parentId: Int? = null
) {
    companion object {
        val Empty = CategoryFilters()
    }
}