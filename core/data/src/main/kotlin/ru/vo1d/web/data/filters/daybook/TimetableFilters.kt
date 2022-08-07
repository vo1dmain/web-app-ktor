package ru.vo1d.web.data.filters.daybook

import ru.vo1d.web.data.filters.Filters
import ru.vo1d.web.entities.daybook.timetable.TimetableFormat

class TimetableFilters private constructor(builder: Builder) : Filters<TimetableFilters>(TimetableFilters::class) {
    val groupCode = builder.groupCode
    val typeId = builder.typeId
    val format = builder.format

    class Builder : Filters.Builder<TimetableFilters> {
        var groupCode: String? = null
        var typeId: String? = null
        var format: TimetableFormat? = null

        override fun build() = TimetableFilters(this)
    }
}

