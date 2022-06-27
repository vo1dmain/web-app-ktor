package ru.penzgtu.web.entities.daybook.timetable.session

import kotlinx.serialization.Serializable

@Serializable
data class SessionModel(
    val id: Int? = null,
    val subject: String,
    val instructor: String,
    val place: String,
    val typeId: Int,
    val dayId: Int,
    val periodId: Int,
    val weekOptionId: Int?
)
