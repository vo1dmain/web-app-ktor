package ru.vo1d.web.entities.daybook.timetable.session

import kotlinx.datetime.DateTimePeriod
import kotlinx.serialization.Serializable

@Serializable
data class SessionModel(
    val id: Int? = null,
    val subject: String,
    val instructor: String,
    val place: String,
    val typeId: Int,
    val dayId: Int,
    val timeId: Int,
    val duration: DateTimePeriod? = null,
    val weekOptionId: Int?
)
