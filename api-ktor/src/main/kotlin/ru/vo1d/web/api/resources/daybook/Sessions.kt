package ru.vo1d.web.api.resources.daybook

import io.ktor.resources.*
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.vo1d.web.domain.daybook.timetable.week.WeekOption
import ru.vo1d.web.api.extensions.failIfNegative
import ru.vo1d.web.api.extensions.failIfNotPositive
import ru.vo1d.web.api.resources.ListResource
import kotlin.time.Instant

@Serializable
@Resource("/sessions/regular")
data class RegularSessions(
    override val page: Int? = null,
    val timetable: Int? = null,
    val subject: String? = null,
    val instructor: String? = null,
    val place: String? = null,
    val type: Int? = null,
    val day: DayOfWeek? = null,
    val time: LocalTime? = null,
    @SerialName("week_option") val weekOption: WeekOption? = null
) : ListResource {
    init {
        page?.failIfNotPositive()
        timetable?.failIfNegative()
        type?.failIfNegative()
    }
}

@Serializable
@Resource("/sessions/dated")
data class DatedSessions(
    override val page: Int? = null,
    val timetable: Int? = null,
    val subject: String? = null,
    val instructor: String? = null,
    val place: String? = null,
    val type: Int? = null,
    val dateTime: Instant? = null
) : ListResource {
    init {
        page?.failIfNotPositive()
        timetable?.failIfNegative()
        type?.failIfNegative()
    }
}
