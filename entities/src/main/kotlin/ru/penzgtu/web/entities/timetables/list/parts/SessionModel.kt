package ru.penzgtu.web.entities.timetables.list.parts

import kotlinx.serialization.Serializable

@Serializable
data class SessionModel(
    val id: Int?,
    val subject: String,
    val instructor: String,
    val place: String,
    val typeId: Int,
    val dayId: Int,
    val periodId: Int,
    val weekOptionId: Int?
)
