package ru.vo1d.web.orm.dao.daybook.timetable

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import ru.vo1d.web.data.dao.SessionDao
import ru.vo1d.web.data.filters.daybook.SessionFilters
import ru.vo1d.web.entities.daybook.timetable.session.SessionModel
import ru.vo1d.web.orm.dao.XpDao
import ru.vo1d.web.orm.entities.daybook.timetable.Session
import ru.vo1d.web.orm.entities.daybook.timetable.Sessions
import ru.vo1d.web.orm.entities.daybook.timetable.TimetableSessions

class SessionDaoXp : SessionDao, XpDao<SessionModel> {
    override suspend fun create(item: SessionModel) = Sessions.insertAndGetId { it.mapItem(item) }.value

    override suspend fun create(vararg items: SessionModel) =
        Sessions.batchInsert(items.asIterable()) { mapItem(it) }.count()

    override suspend fun read(id: Int) = Session.findById(id)?.toModel()

    override suspend fun update(item: SessionModel) =
        Sessions.update({ Sessions.id eq item.id }) { it.mapItem(item) }

    override suspend fun delete(id: Int) = Sessions.deleteWhere { Sessions.id eq id }

    override suspend fun list(offset: Long, limit: Int) = Session.all().limit(limit, offset).map(Session::toModel)

    override suspend fun filter(filters: SessionFilters, offset: Long, limit: Int): List<SessionModel> {
        if (filters.areEmpty()) return list(offset, limit)

        val query = Sessions.selectAll().apply {
            filters.timetableId?.let {
                adjustColumnSet { innerJoin(TimetableSessions) }.adjustSlice { slice(Sessions.columns) }
            }
            filters.dayId?.let {
                andWhere { Sessions.dayId eq it }
            }
            filters.periodId?.let {
                andWhere { Sessions.periodId eq it }
            }
            filters.typeId?.let {
                andWhere { Sessions.typeId eq it }
            }
            filters.weekOptionId?.let {
                andWhere { Sessions.weekOptionId eq it }
            }
        }

        return Session.wrapRows(query).limit(limit, offset).map(Session::toModel)
    }

    override fun UpdateBuilder<Int>.mapItem(item: SessionModel) {
        this[Sessions.subject] = item.subject
        this[Sessions.instructor] = item.instructor
        this[Sessions.place] = item.place
        this[Sessions.typeId] = item.typeId
        this[Sessions.dayId] = item.dayId
        this[Sessions.periodId] = item.periodId
        this[Sessions.weekOptionId] = item.weekOptionId
    }
}