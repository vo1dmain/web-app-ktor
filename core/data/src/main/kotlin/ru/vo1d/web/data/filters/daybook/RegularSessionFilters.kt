package ru.vo1d.web.data.filters.daybook

import kotlinx.datetime.DayOfWeek
import ru.vo1d.web.entities.daybook.timetable.week.WeekOption


data class RegularSessionFilters(
    val timetableId: Int? = null,
    val subject: String? = null,
    val instructor: String? = null,
    val place: String? = null,
    val typeId: Int? = null,
    val dayOfWeek: DayOfWeek? = null,
    val timeId: Int? = null,
    val weekOption: WeekOption? = null
) {
    companion object {
        val Empty = RegularSessionFilters()
    }
}