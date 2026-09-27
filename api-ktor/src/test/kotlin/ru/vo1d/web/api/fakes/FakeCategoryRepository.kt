package ru.vo1d.web.api.fakes

import ru.vo1d.web.domain.PageRequest
import ru.vo1d.web.domain.news.CategoryFilters
import ru.vo1d.web.domain.news.CategoryRepository
import ru.vo1d.web.domain.news.category.Category

class FakeCategoryRepository : CategoryRepository {
    override suspend fun find(filters: CategoryFilters, page: PageRequest) = all()
        .filter { category -> filters.parentId?.let { it == category.parentId } ?: true }
        .page(page)

    override suspend fun get(id: Int) = all().firstOrNull { it.id == id }

    private suspend fun all() = Fixtures.list("/data/categories.json", Category.serializer())
}
