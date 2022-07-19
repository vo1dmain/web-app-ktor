package ru.vo1d.web.entities.daybook.timetable.session

import kotlinx.serialization.Serializable

@Serializable
data class TimetableSessionModel(
    val timetableId: Int? = null,
    val sessionId: Int
)
