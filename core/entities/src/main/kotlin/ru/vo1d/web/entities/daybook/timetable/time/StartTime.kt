package ru.vo1d.web.entities.daybook.timetable.time

import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class StartTime(
    val id: Int? = null,
    val time: Instant
)
