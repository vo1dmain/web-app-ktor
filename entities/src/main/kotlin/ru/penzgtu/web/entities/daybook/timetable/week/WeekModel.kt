package ru.penzgtu.web.entities.daybook.timetable.week

import kotlinx.serialization.Serializable

@Serializable
data class WeekModel(
    val number: Int,
    val title: String
)
