package ru.vo1d.web.persistence.daybook

import org.jetbrains.exposed.v1.core.ReferenceOption
import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.dao.IntEntityClass
import org.jetbrains.exposed.v1.datetime.duration
import org.jetbrains.exposed.v1.datetime.timestamp
import ru.vo1d.web.domain.DEFAULT_DURATION
import ru.vo1d.web.domain.daybook.timetable.session.DatedSession

internal object DatedSessions : IntIdTable() {
    val subject = varchar("subject", 160)
    val instructor = varchar("instructor", 64)
    val place = varchar("place", 32)
    val typeId = reference("typeId", SessionTypes, ReferenceOption.CASCADE, ReferenceOption.CASCADE)
    val dateTime = timestamp("datetime")
    val duration = duration("duration").default(DEFAULT_DURATION)
}

internal object TimetableDatedSessions : Table() {
    val timetableId = reference("timetableId", Timetables)
    val sessionId = reference("sessionId", DatedSessions)

    override val primaryKey = PrimaryKey(timetableId, sessionId)
}

internal class DatedSessionEntity(id: EntityID<Int>) : SessionEntity<DatedSession>(id) {
    companion object : IntEntityClass<DatedSessionEntity>(DatedSessions)

    val subject by DatedSessions.subject
    val instructor by DatedSessions.instructor
    val place by DatedSessions.place
    val typeId by DatedSessions.typeId
    val duration by DatedSessions.duration
    val dateTime by DatedSessions.dateTime
}