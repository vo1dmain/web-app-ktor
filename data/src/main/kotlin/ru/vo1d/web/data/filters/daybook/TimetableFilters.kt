package ru.vo1d.web.data.filters.daybook

import ru.vo1d.web.data.dao.filters.Filters
import ru.vo1d.web.data.dao.filters.FiltersBuilder

class TimetableFilters private constructor() : Filters<TimetableFilters>(TimetableFilters::class) {
    companion object : FiltersBuilder<TimetableFilters>(TimetableFilters::class)

    var groupCode: String? = null
        private set
    var typeId: String? = null
        private set

    fun groupCode(init: () -> String?) {
        groupCode = init()
    }

    fun typeId(init: () -> String?) {
        typeId = init()
    }
}

