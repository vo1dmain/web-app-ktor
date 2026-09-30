package ru.vo1d.web.api.fakes

import ru.vo1d.web.domain.PageRequest
import ru.vo1d.web.domain.news.CategoryFilters
import ru.vo1d.web.domain.news.CategoryRepository

class FakeCategoryRepository : CategoryRepository {
    private val categories = TestData.categories

    override suspend fun find(filters: CategoryFilters, page: PageRequest) = categories
        .filter { category -> filters.parentId?.let { it == category.parentId } ?: true }
        .page(page)

    override suspend fun get(id: Int) = categories.firstOrNull { it.id == id }
}
