package ru.penzgtu.web.entities.daybook.timetable.day

import kotlinx.serialization.Serializable

@Serializable
data class DayModel(
    val id: Int? = null,
    val title: String
)
