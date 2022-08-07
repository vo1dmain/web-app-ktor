package ru.vo1d.web.entities.daybook.timetable.time

import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.serialization.Serializable

@Serializable
data class StartTime(val id: Int? = null, val time: LocalTime, val timeZone: TimeZone? = null)
