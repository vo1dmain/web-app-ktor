package ru.penzgtu.web.entities.timetables.meta.parts

import kotlinx.serialization.Serializable

@Serializable
data class TimePeriodModel(
    val id: Int?,
    val start: String,
    val end: String,
)