package ru.vo1d.web.entities.daybook.timetable.period

import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.serialization.Serializable

@Serializable
data class TimePeriodModel(
    val id: Int? = null,
    val start: LocalTime,
    val end: LocalTime,
    val timeZone: TimeZone? = null
)