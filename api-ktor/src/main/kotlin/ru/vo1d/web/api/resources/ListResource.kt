package ru.vo1d.web.api.resources

import ru.vo1d.web.domain.PageRequest

interface ListResource {
    val page: Int?
}

fun ListResource.pageRequest() = PageRequest(page ?: 1)
