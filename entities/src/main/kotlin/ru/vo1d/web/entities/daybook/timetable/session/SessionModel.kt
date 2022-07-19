package ru.vo1d.web.entities.daybook.timetable.session

import kotlinx.datetime.DateTimePeriod
import kotlinx.datetime.toDateTimePeriod
import kotlinx.serialization.Serializable
import kotlin.time.Duration.Companion.minutes

@Serializable
data class SessionModel(
    val id: Int? = null,
    val subject: String,
    val instructor: String,
    val place: String,
    val typeId: Int,
    val dayId: Int,
    val timeId: Int,
    val duration: DateTimePeriod = 45.minutes.toDateTimePeriod(),
    val weekOptionId: Int?
)
