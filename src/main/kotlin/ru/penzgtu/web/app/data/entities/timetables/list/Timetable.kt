package ru.penzgtu.web.app.data.entities.timetables.list

import kotlinx.serialization.Serializable
import ru.penzgtu.web.app.data.entities.timetables.list.parts.Day
import ru.penzgtu.web.app.data.entities.timetables.list.parts.Session

@Serializable
data class Timetable(
    val id: Int?,
    val groupCode: String,
    val typeId: Int,
    val days: List<Day>,
    val sessions: List<Session>,
)
