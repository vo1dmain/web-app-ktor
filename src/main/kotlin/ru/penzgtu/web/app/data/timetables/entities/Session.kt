package ru.penzgtu.web.app.data.timetables.entities

import kotlinx.serialization.Serializable

@Serializable
data class Session(
    val subject: String,
    val instructor: String,
    val place: String,
    val typeId: Int,
    val dayId: Int,
    val periodId: Int,
    val weekOptionId: Int
)
