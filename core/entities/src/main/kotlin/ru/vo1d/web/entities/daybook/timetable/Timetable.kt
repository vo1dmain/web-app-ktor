package ru.vo1d.web.entities.daybook.timetable

import kotlinx.serialization.Serializable

@Serializable
class Timetable(
    val id: Int? = null,
    val groupCode: String,
    val typeId: String,
    val format: TimetableFormat
)