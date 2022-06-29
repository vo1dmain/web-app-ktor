package ru.vo1d.web.entities.daybook.timetable

import kotlinx.serialization.Serializable
import ru.vo1d.web.entities.daybook.timetable.day.DayModel
import ru.vo1d.web.entities.daybook.timetable.session.SessionModel

@Serializable
data class TimetableDto(
    val id: Int,
    val groupCode: String,
    val typeId: String,
    val days: List<DayModel>,
    val sessions: List<SessionModel>,
)
