package ru.penzgtu.web.entities.daybook.timetable

import kotlinx.serialization.Serializable
import ru.penzgtu.web.entities.daybook.timetable.day.DayModel
import ru.penzgtu.web.entities.daybook.timetable.session.SessionModel

@Serializable
data class TimetableModel(
    val id: Int?,
    val groupCode: String,
    val typeId: String,
    val days: List<DayModel>,
    val sessions: List<SessionModel>,
)
