package ru.vo1d.web.domain.news

import ru.vo1d.web.domain.PageRequest
import ru.vo1d.web.domain.news.category.Category

interface CategoryRepository {
    suspend fun find(filters: CategoryFilters, page: PageRequest): List<Category>

    suspend fun get(id: Int): Category?
}
