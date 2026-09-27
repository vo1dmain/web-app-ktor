package ru.vo1d.web.domain.filters.news

data class CategoryFilters(
    val parentId: Int? = null
) {
    companion object {
        val Empty = CategoryFilters()
    }
}