package ru.penzgtu.web.entities.daybook.timetable.period

import kotlinx.serialization.Serializable

@Serializable
data class TimePeriodModel(
    val id: Int?,
    val start: String,
    val end: String,
)