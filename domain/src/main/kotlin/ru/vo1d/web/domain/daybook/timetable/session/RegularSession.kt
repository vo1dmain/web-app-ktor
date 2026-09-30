package ru.vo1d.web.domain.daybook.timetable.session

import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalTime
import ru.vo1d.web.domain.daybook.timetable.week.WeekOption
import kotlin.time.Duration

/**
 * A session repeating every week on [dayOfWeek] at [time] (local to the daybook zone).
 */
data class RegularSession(
    override val id: Int,
    override val subject: String,
    override val instructor: String,
    override val place: String,
    override val typeId: Int,
    override val duration: Duration,
    val dayOfWeek: DayOfWeek,
    val time: LocalTime,
    val weekOption: WeekOption
) : Session

/**
 * @param duration null for the default session length
 */
data class NewRegularSession(
    val subject: String,
    val instructor: String,
    val place: String,
    val typeId: Int,
    val duration: Duration?,
    val dayOfWeek: DayOfWeek,
    val time: LocalTime,
    val weekOption: WeekOption
)
