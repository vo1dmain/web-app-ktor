package ru.vo1d.web.domain.daybook.timetable.session

import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.vo1d.web.domain.daybook.timetable.week.WeekOption
import kotlin.time.Duration

@Serializable
@SerialName("Regular")
data class RegularSession(
    val id: Int? = null,
    override val subject: String,
    override val instructor: String,
    override val place: String,
    override val typeId: Int,
    override val duration: Duration? = null,
    val dayOfWeek: DayOfWeek,
    val time: LocalTime,
    val weekOption: WeekOption
) : Session
