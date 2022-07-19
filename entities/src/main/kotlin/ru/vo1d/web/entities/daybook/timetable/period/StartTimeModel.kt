package ru.vo1d.web.entities.daybook.timetable.period

import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.serialization.Serializable

@Serializable
data class StartTimeModel(val id: Int? = null, val time: LocalTime, val timeZone: TimeZone? = null)
