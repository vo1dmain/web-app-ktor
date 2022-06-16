package ru.penzgtu.web.app.data.entities.timetables.list

import kotlinx.serialization.Serializable

@Serializable
data class TimetableView(
    val id: Int,
    val groupCode: String,
    val typeId: Int
)