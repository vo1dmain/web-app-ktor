package ru.vo1d.web.data.filters.daybook

import ru.vo1d.web.data.dao.filters.Filters
import ru.vo1d.web.data.dao.filters.FiltersBuilder


class SessionFilters private constructor() : Filters<SessionFilters>(SessionFilters::class) {
    companion object : FiltersBuilder<SessionFilters>(SessionFilters::class)

    var timetableId: Int? = null
        private set
    var typeId: Int? = null
        private set
    var dayId: Int? = null
        private set
    var periodId: Int? = null
        private set
    var weekOptionId: Int? = null
        private set

    fun timetableId(init: () -> Int?) {
        timetableId = init()
    }

    fun typeId(init: () -> Int?) {
        typeId = init()
    }

    fun dayId(init: () -> Int?) {
        dayId = init()
    }

    fun periodId(init: () -> Int?) {
        periodId = init()
    }

    fun weekOptionId(init: () -> Int?) {
        weekOptionId = init()
    }
}