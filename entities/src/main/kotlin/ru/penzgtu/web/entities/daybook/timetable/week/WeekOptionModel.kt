package ru.penzgtu.web.entities.daybook.timetable.week

import kotlinx.serialization.Serializable

@Serializable
data class WeekOptionModel(
    val id: Int? = null,
    val description: String
)
