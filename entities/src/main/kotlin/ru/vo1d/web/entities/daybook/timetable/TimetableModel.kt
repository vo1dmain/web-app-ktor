package ru.vo1d.web.entities.daybook.timetable

import kotlinx.serialization.Serializable

@Serializable
data class TimetableModel(
    val id: Int? = null,
    val groupCode: String,
    val typeId: String
)