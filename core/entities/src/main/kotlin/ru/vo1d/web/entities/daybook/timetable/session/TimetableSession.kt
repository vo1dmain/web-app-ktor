package ru.vo1d.web.entities.daybook.timetable.session

import kotlinx.serialization.Serializable

@Serializable
data class TimetableSession(
    val timetableId: Int,
    val sessionId: Int
)
