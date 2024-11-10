package ru.vo1d.web.exposed.mappers

import kotlinx.datetime.TimeZone
import kotlinx.datetime.toDateTimePeriod
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import ru.vo1d.web.entities.daybook.timetable.session.DatedSession
import ru.vo1d.web.entities.daybook.timetable.session.RegularSession
import ru.vo1d.web.entities.daybook.timetable.session.SessionType
import ru.vo1d.web.entities.daybook.timetable.time.StartTime
import ru.vo1d.web.entities.extensions.toDuration
import ru.vo1d.web.exposed.entities.daybook.timetable.*

internal fun UpdateBuilder<*>.mapItem(item: DatedSession) {
    this[DatedSessions.subject] = item.subject
    this[DatedSessions.instructor] = item.instructor
    this[DatedSessions.place] = item.place
    this[DatedSessions.typeId] = item.typeId
    item.duration?.let { this[DatedSessions.duration] = it.toDuration() }
    this[DatedSessions.datetime] = item.datetime
    item.timeZone?.let { this[DatedSessions.timeZone] = it.id }
}

internal fun UpdateBuilder<*>.mapItem(item: RegularSession) {
    this[RegularSessions.subject] = item.subject
    this[RegularSessions.instructor] = item.instructor
    this[RegularSessions.place] = item.place
    this[RegularSessions.typeId] = item.typeId
    item.duration?.let { this[RegularSessions.duration] = it.toDuration() }
    this[RegularSessions.dayOfWeek] = item.dayOfWeek
    this[RegularSessions.timeId] = item.timeId
    this[RegularSessions.weekOption] = item.weekOption
}

internal fun UpdateBuilder<*>.mapItem(item: SessionType) {
    this[SessionTypes.title] = item.title
}

internal fun UpdateBuilder<*>.mapItem(item: StartTime) {
    this[SessionStartTimes.time] = item.time
    item.timeZone?.let { this[SessionStartTimes.timeZone] = it.id }
}

internal fun DatedSessionEntity.toDomain() = DatedSession(
    id.value,
    subject,
    instructor,
    place,
    typeId.value,
    duration.toDateTimePeriod(),
    datetime,
    TimeZone.of(timeZone)
)

internal fun RegularSessionEntity.toDomain() = RegularSession(
    id.value,
    subject,
    instructor,
    place,
    typeId.value,
    duration.toDateTimePeriod(),
    dayOfWeek,
    timeId.value,
    weekOption
)

internal fun SessionTypeEntity.toDomain() = SessionType(id.value, title)

internal fun StartTimeEntity.toDomain() = StartTime(id.value, time, TimeZone.of(timeZone))