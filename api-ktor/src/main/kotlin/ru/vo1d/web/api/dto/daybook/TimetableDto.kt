package ru.vo1d.web.api.dto.daybook

import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalTime
import kotlinx.serialization.Serializable
import ru.vo1d.web.domain.daybook.timetable.NewTimetable
import ru.vo1d.web.domain.daybook.timetable.Timetable
import ru.vo1d.web.domain.daybook.timetable.TimetableFormat
import ru.vo1d.web.domain.daybook.timetable.session.*
import ru.vo1d.web.domain.daybook.timetable.week.WeekOption
import kotlin.time.Duration
import kotlin.time.Instant

@Serializable
data class TimetableRequest(
    val groupCode: String,
    val typeId: String,
    val format: TimetableFormat
)

@Serializable
data class TimetableResponse(
    val id: Int,
    val groupCode: String,
    val typeId: String,
    val format: TimetableFormat
)

/**
 * Link between a timetable and a session, sent to and returned from `/timetables/{id}/sessions`.
 */
@Serializable
data class TimetableSessionDto(
    val timetableId: Int,
    val sessionId: Int
)

/**
 * @param duration omitted for the default session length
 */
@Serializable
data class RegularSessionRequest(
    val subject: String,
    val instructor: String,
    val place: String,
    val typeId: Int,
    val duration: Duration? = null,
    val dayOfWeek: DayOfWeek,
    val time: LocalTime,
    val weekOption: WeekOption
)

@Serializable
data class RegularSessionResponse(
    val id: Int,
    val subject: String,
    val instructor: String,
    val place: String,
    val typeId: Int,
    val duration: Duration,
    val dayOfWeek: DayOfWeek,
    val time: LocalTime,
    val weekOption: WeekOption
)

/**
 * @param duration omitted for the default session length
 */
@Serializable
data class DatedSessionRequest(
    val subject: String,
    val instructor: String,
    val place: String,
    val typeId: Int,
    val duration: Duration? = null,
    val dateTime: Instant
)

@Serializable
data class DatedSessionResponse(
    val id: Int,
    val subject: String,
    val instructor: String,
    val place: String,
    val typeId: Int,
    val duration: Duration,
    val dateTime: Instant
)

fun TimetableRequest.toDomain() = NewTimetable(groupCode, typeId, format)

fun Timetable.toResponse() = TimetableResponse(id, groupCode, typeId, format)

fun TimetableSessionDto.toDomain() = TimetableSession(timetableId, sessionId)

fun TimetableSession.toDto() = TimetableSessionDto(timetableId, sessionId)

fun RegularSessionRequest.toDomain() =
    NewRegularSession(subject, instructor, place, typeId, duration, dayOfWeek, time, weekOption)

fun RegularSession.toResponse() =
    RegularSessionResponse(id, subject, instructor, place, typeId, duration, dayOfWeek, time, weekOption)

fun DatedSessionRequest.toDomain() = NewDatedSession(subject, instructor, place, typeId, duration, dateTime)

fun DatedSession.toResponse() = DatedSessionResponse(id, subject, instructor, place, typeId, duration, dateTime)
