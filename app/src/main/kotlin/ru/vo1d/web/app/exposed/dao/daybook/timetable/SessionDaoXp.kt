package ru.vo1d.web.app.exposed.dao.daybook.timetable

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import ru.vo1d.web.app.data.dao.SessionDao
import ru.vo1d.web.app.data.filters.daybook.SessionFilters
import ru.vo1d.web.app.exposed.orm.daybook.timetable.Session
import ru.vo1d.web.app.exposed.orm.daybook.timetable.Sessions
import ru.vo1d.web.app.exposed.orm.daybook.timetable.TimetableSessions
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
    override suspend fun list(offset: Long, limit: Int) = Session.all().limit(limit, offset).map(Session::toModel)
    override suspend fun filter(filters: SessionFilters, offset: Long, limit: Int): List<SessionModel> {
        if (filters.areEmpty()) return list(offset, limit)

        val filterQuery = (Sessions.dayId eq filters.dayId) or
                (Sessions.periodId eq filters.periodId) or
                (Sessions.typeId eq filters.typeId) or
                (Sessions.weekOptionId eq filters.weekOptionId)

        val query = Sessions.innerJoin(TimetableSessions).slice(Sessions.columns)
            .select { (TimetableSessions.timetableId eq filters.timetableId) and filterQuery }
        return Session.wrapRows(query).limit(limit, offset).map(Session::toModel)
    }
}