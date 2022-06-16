package ru.penzgtu.web.app.data.entities.timetables.meta.parts

import kotlinx.serialization.Serializable

@Serializable
data class WeekOption(
    val id: Int?,
    val description: String
)
