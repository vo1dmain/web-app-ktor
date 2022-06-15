package ru.penzgtu.web.app.data.timetables.meta.parts

import kotlinx.serialization.Serializable

@Serializable
data class TimePeriod(
    val start: String,
    val end: String,
)