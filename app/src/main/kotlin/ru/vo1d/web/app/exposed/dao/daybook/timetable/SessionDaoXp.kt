package ru.vo1d.web.app.exposed.dao.daybook.timetable

import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.update
import ru.vo1d.web.app.data.dao.SessionDao
import ru.vo1d.web.app.exposed.orm.daybook.timetable.Session
import ru.vo1d.web.app.exposed.orm.daybook.timetable.Sessions
import ru.vo1d.web.entities.daybook.timetable.session.SessionModel

class SessionDaoXp : SessionDao {
    override suspend fun create(item: SessionModel) = Sessions.insertAndGetId {
        it[subject] = item.subject
        it[instructor] = item.instructor
        it[place] = item.place
        it[typeId] = item.typeId
        it[dayId] = item.dayId
        it[periodId] = item.periodId
        it[weekOptionId] = item.weekOptionId
    }.value

    override suspend fun read(id: Int) = Session.findById(id)?.toModel()

    override suspend fun update(item: SessionModel) = Sessions.update({ Sessions.id eq item.id }) {
        it[subject] = item.subject
        it[instructor] = item.instructor
        it[place] = item.place
        it[typeId] = item.typeId
        it[dayId] = item.dayId
        it[periodId] = item.periodId
        it[weekOptionId] = item.weekOptionId
    }

    override suspend fun delete(id: Int) = Sessions.deleteWhere { Sessions.id eq id }
}