package ru.vo1d.web.data.filters.daybook

import ru.vo1d.web.data.dao.filters.Filters
import ru.vo1d.web.data.dao.filters.FiltersBuilder
import ru.vo1d.web.data.dao.filters.FiltersCreator

interface TimetableFilters : Filters {
    companion object : FiltersCreator<TimetableFilters, Builder>(Builder::class)

    val groupCode: String?
    val typeId: String?

    override fun areEmpty() = listOf(groupCode, typeId).all { it == null }

    class Builder internal constructor() : FiltersBuilder<TimetableFilters> {
        var groupCode: String? = null
        var typeId: String? = null

        override fun build() = object : TimetableFilters {
            override val groupCode = this@Builder.groupCode
            override val typeId = this@Builder.typeId
        }
    }
}

