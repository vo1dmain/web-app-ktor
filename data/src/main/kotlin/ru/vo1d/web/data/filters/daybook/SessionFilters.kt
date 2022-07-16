package ru.vo1d.web.data.filters.daybook

import ru.vo1d.web.data.filters.Filters
import ru.vo1d.web.data.filters.FiltersBuilder


class SessionFilters private constructor(builder: Builder) : Filters<SessionFilters>(SessionFilters::class) {
    val timetableId = builder.timetableId
    val typeId = builder.typeId
    val dayId = builder.dayId
    val periodId = builder.periodId
    val weekOptionId = builder.weekOptionId

    class Builder : FiltersBuilder<SessionFilters> {
        var timetableId: Int? = null
        var typeId: Int? = null
        var dayId: Int? = null
        var periodId: Int? = null
        var weekOptionId: Int? = null
        override fun build() = SessionFilters(this)
    }
}