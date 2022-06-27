package ru.penzgtu.web.entities.daybook.timetable.session

import kotlinx.serialization.Serializable

@Serializable
data class SessionTypeModel(
    val id: Int?,
    val title: String
)