package ru.vo1d.web.entities.daybook.timetable.session

import kotlinx.datetime.DateTimePeriod
import kotlinx.datetime.DayOfWeek
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.vo1d.web.entities.daybook.timetable.week.WeekOption

@Serializable
@SerialName("Regular")
data class RegularSessionModel(
    val id: Int? = null,
    override val subject: String,
    override val instructor: String,
    override val place: String,
    override val typeId: Int,
    override val duration: DateTimePeriod? = null,
    val dayOfWeek: DayOfWeek,
    val timeId: Int,
    val weekOption: WeekOption
) : SessionModel
