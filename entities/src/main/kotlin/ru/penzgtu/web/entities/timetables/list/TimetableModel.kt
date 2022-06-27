package ru.penzgtu.web.entities.timetables.list

import kotlinx.serialization.Serializable
import ru.penzgtu.web.entities.timetables.list.parts.DayModel
import ru.penzgtu.web.entities.timetables.list.parts.SessionModel

@Serializable
data class TimetableModel(
    val id: Int?,
    val groupCode: String,
    val typeId: Int,
    val days: List<DayModel>,
    val sessions: List<SessionModel>,
)
