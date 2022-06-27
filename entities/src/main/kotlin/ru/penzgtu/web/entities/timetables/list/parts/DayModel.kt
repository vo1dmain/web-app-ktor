package ru.penzgtu.web.entities.timetables.list.parts

import kotlinx.serialization.Serializable

@Serializable
data class DayModel(
    val id: Int?,
    val title: String
)
