package ru.vo1d.web.domain.daybook.timetable.session

import kotlinx.serialization.Serializable

@Serializable
data class TimetableSession(
    val timetableId: Int,
    val sessionId: Int
)
