package ru.penzgtu.web.app.data.timetables.entities

import kotlinx.serialization.Serializable

@Serializable
class TimetableView(
    val id: Int,
    val groupCode: String,
    val typeId: Int
)