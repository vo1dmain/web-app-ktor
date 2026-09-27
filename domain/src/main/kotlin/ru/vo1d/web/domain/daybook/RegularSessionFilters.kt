package ru.vo1d.web.domain.daybook

import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalTime
import ru.vo1d.web.domain.daybook.timetable.week.WeekOption


data class RegularSessionFilters(
    val timetableId: Int? = null,
    val subject: String? = null,
    val instructor: String? = null,
    val place: String? = null,
    val typeId: Int? = null,
    val dayOfWeek: DayOfWeek? = null,
    val time: LocalTime? = null,
    val weekOption: WeekOption? = null
)
