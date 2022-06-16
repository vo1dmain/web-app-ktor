package ru.penzgtu.web.app.data.entities.timetables.meta.parts

import kotlinx.serialization.Serializable

@Serializable
data class TimePeriod(
    val id: Int?,
    val start: String,
    val end: String,
)