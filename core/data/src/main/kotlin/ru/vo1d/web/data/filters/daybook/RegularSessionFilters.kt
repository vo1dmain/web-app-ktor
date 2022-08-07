package ru.vo1d.web.data.filters.daybook

import kotlinx.datetime.DayOfWeek
import ru.vo1d.web.data.filters.Filters
import ru.vo1d.web.entities.daybook.timetable.week.WeekOption


class RegularSessionFilters private constructor(builder: Builder) :
    Filters<RegularSessionFilters>(RegularSessionFilters::class) {
    val timetableId = builder.timetableId
    val subject = builder.subject
    val instructor = builder.instructor
    val place = builder.place
    val typeId = builder.typeId
    val dayOfWeek = builder.dayOfWeek
    val timeId = builder.timeId
    val weekOptionId = builder.weekOption

    class Builder : Filters.Builder<RegularSessionFilters> {
        var timetableId: Int? = null
        var subject: String? = null
        var instructor: String? = null
        var place: String? = null
        var typeId: Int? = null
        var dayOfWeek: DayOfWeek? = null
        var timeId: Int? = null
        var weekOption: WeekOption? = null

        override fun build() = RegularSessionFilters(this)
    }
}