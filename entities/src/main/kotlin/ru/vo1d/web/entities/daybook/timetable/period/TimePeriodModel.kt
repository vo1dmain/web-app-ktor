package ru.vo1d.web.entities.daybook.timetable.period

import kotlinx.serialization.Serializable

@Serializable
data class TimePeriodModel(
    val id: Int? = null,
    val start: String,
    val end: String,
)