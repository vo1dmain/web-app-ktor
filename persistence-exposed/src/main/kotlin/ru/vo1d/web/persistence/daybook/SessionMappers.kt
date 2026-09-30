package ru.vo1d.web.persistence.daybook

import org.jetbrains.exposed.v1.core.statements.UpdateBuilder
import ru.vo1d.web.domain.daybook.timetable.session.DatedSession
import ru.vo1d.web.domain.daybook.timetable.session.NewDatedSession
import ru.vo1d.web.domain.daybook.timetable.session.NewRegularSession
import ru.vo1d.web.domain.daybook.timetable.session.RegularSession
import ru.vo1d.web.domain.daybook.timetable.session.SessionType

internal fun UpdateBuilder<*>.mapItem(item: NewDatedSession) {
    this[DatedSessions.subject] = item.subject
    this[DatedSessions.instructor] = item.instructor
    this[DatedSessions.place] = item.place
    this[DatedSessions.typeId] = item.typeId
    item.duration?.let { this[DatedSessions.duration] = it }
    this[DatedSessions.dateTime] = item.dateTime
}

internal fun UpdateBuilder<*>.mapItem(item: NewRegularSession) {
    this[RegularSessions.subject] = item.subject
    this[RegularSessions.instructor] = item.instructor
    this[RegularSessions.place] = item.place
    this[RegularSessions.typeId] = item.typeId
    item.duration?.let { this[RegularSessions.duration] = it }
    this[RegularSessions.dayOfWeek] = item.dayOfWeek
    this[RegularSessions.time] = item.time
    this[RegularSessions.weekOption] = item.weekOption
}

internal fun UpdateBuilder<*>.mapItem(item: SessionType) {
    this[SessionTypes.title] = item.title
}

internal fun DatedSessionEntity.toDomain() = DatedSession(
    id.value,
    subject,
    instructor,
    place,
    typeId.value,
    duration,
    dateTime
)

internal fun RegularSessionEntity.toDomain() = RegularSession(
    id.value,
    subject,
    instructor,
    place,
    typeId.value,
    duration,
    dayOfWeek,
    time,
    weekOption
)

internal fun SessionTypeEntity.toDomain() = SessionType(id.value, title)