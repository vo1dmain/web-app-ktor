package ru.vo1d.web.orm.entities.daybook.timetable

import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.toDateTimePeriod
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption.CASCADE
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.duration
import ru.vo1d.web.entities.DEFAULT_DURATION
import ru.vo1d.web.entities.daybook.timetable.session.RegularSessionModel
import ru.vo1d.web.entities.daybook.timetable.week.WeekOption

object RegularSessions : IntIdTable() {
    val subject = varchar("subject", 64)
    val instructor = varchar("instructor", 64)
    val place = varchar("place", 24)
    val typeId = reference("typeId", SessionTypes, CASCADE, CASCADE)
    val dayOfWeek = enumerationByName("dayOfWeek", 10, DayOfWeek::class)
    val timeId = reference("timeId", SessionStartTimes, CASCADE, CASCADE)
    val duration = duration("duration").default(DEFAULT_DURATION)
    val weekOption = enumerationByName("weekOption", 12, WeekOption::class)
}

class RegularSession(id: EntityID<Int>) : IntEntity(id), SessionEntity<RegularSessionModel> {
    companion object : IntEntityClass<RegularSession>(RegularSessions)

    val subject by RegularSessions.subject
    val instructor by RegularSessions.instructor
    val place by RegularSessions.place
    val typeId by RegularSessions.typeId
    val dayOfWeek by RegularSessions.dayOfWeek
    val timeId by RegularSessions.timeId
    val duration by RegularSessions.duration
    val weekOption by RegularSessions.weekOption

    override fun toModel() = RegularSessionModel(
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
}

object TimetableRegularSessions : Table() {
    val timetableId = reference("timetableId", Timetables)
    val sessionId = reference("sessionId", RegularSessions)

    override val primaryKey = PrimaryKey(timetableId, sessionId)
}