package ru.vo1d.web.exposed.entities.daybook.timetable

import kotlinx.datetime.TimeZone
import kotlinx.datetime.toDateTimePeriod
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.datetime
import org.jetbrains.exposed.sql.kotlin.datetime.duration
import ru.vo1d.web.entities.DEFAULT_DURATION
import ru.vo1d.web.entities.daybook.timetable.session.DatedSession

object DatedSessions : IntIdTable() {
    val subject = varchar("subject", 160)
    val instructor = varchar("instructor", 64)
    val place = varchar("place", 32)
    val typeId = reference("typeId", SessionTypes, ReferenceOption.CASCADE, ReferenceOption.CASCADE)
    val datetime = datetime("datetime")
    val timeZone = varchar("timeZone", 32).default(TimeZone.currentSystemDefault().id)
    val duration = duration("duration").default(DEFAULT_DURATION)
}

class DatedSessionEntity(id: EntityID<Int>) : IntEntity(id), SessionEntity<DatedSession> {
    companion object : IntEntityClass<DatedSessionEntity>(DatedSessions)

    val subject by DatedSessions.subject
    val instructor by DatedSessions.instructor
    val place by DatedSessions.place
    val typeId by DatedSessions.typeId
    val duration by DatedSessions.duration
    val datetime by DatedSessions.datetime
    val timeZone by DatedSessions.timeZone

    override fun toModel() = DatedSession(
        id.value,
        subject,
        instructor,
        place,
        typeId.value,
        duration.toDateTimePeriod(),
        datetime,
        TimeZone.of(timeZone)
    )
}

object TimetableDatedSessions : Table() {
    val timetableId = reference("timetableId", Timetables)
    val sessionId = reference("sessionId", DatedSessions)

    override val primaryKey = PrimaryKey(timetableId, sessionId)
}