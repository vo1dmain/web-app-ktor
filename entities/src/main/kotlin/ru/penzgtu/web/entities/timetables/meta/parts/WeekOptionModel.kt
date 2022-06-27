package ru.penzgtu.web.entities.timetables.meta.parts

import kotlinx.serialization.Serializable

@Serializable
data class WeekOptionModel(
    val id: Int?,
    val description: String
)
