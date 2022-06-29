package ru.vo1d.web.app.exposed.orm.daybook.timetable

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption.CASCADE
import org.jetbrains.exposed.sql.Table
import ru.vo1d.web.app.exposed.orm.HasModel
import ru.vo1d.web.entities.daybook.timetable.session.SessionModel

object Sessions : IntIdTable() {
    val subject = varchar("subject", 64)
    val instructor = varchar("instructor", 64)
    val place = varchar("place", 32)
    val typeId = reference("typeId", SessionTypes, CASCADE, CASCADE)
    val dayId = reference("dayId", Days, CASCADE, CASCADE)
    val periodId = reference("periodId", TimePeriods, CASCADE, CASCADE)
    val weekOptionId = optReference("weekOption", WeekOptions, CASCADE, CASCADE)
}

class Session(id: EntityID<Int>) : IntEntity(id), HasModel<SessionModel> {
    companion object : IntEntityClass<Session>(Sessions)

    val subject by Sessions.subject
    val instructor by Sessions.instructor
    val place by Sessions.place
    val typeId by Sessions.typeId
    val dayId by Sessions.dayId
    val periodId by Sessions.periodId
    val weekOptionId by Sessions.weekOptionId

    override fun toModel() = SessionModel(
        id.value, subject, instructor, place, typeId.value, dayId.value, periodId.value, weekOptionId?.value
    )
}

object TimetableSessions : Table() {
    val timetableId = reference("timetableId", Timetables)
    val sessionId = reference("sessionId", Sessions)

    override val primaryKey = PrimaryKey(timetableId, sessionId)
}