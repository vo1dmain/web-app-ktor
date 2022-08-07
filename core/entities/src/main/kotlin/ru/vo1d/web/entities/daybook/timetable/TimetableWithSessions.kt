package ru.vo1d.web.entities.daybook.timetable

import kotlinx.serialization.Serializable
import ru.vo1d.web.entities.daybook.timetable.session.Session

@Serializable
data class TimetableWithSessions<out T : Session>(
    val id: Int,
    val groupCode: String,
    val typeId: String,
    val format: TimetableFormat,
    val sessions: List<T>,
)
