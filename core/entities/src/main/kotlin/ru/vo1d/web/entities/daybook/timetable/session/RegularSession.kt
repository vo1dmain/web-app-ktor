package ru.vo1d.web.entities.daybook.timetable.session

import kotlinx.datetime.DayOfWeek
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.vo1d.web.entities.daybook.timetable.week.WeekOption
import kotlin.time.Duration
import kotlin.time.Instant

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
    val time: Instant,
    val weekOption: WeekOption
) : Session
