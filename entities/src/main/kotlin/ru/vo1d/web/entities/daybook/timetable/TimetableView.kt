package ru.vo1d.web.entities.daybook.timetable

import kotlinx.serialization.Serializable

@Serializable
data class TimetableView(
    val id: Int,
    val groupCode: String,
    val typeId: String
)