package ru.vo1d.web.exposed.dao.daybook.timetable.regular

import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertIgnore
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import ru.vo1d.web.data.dao.TimetableRegularSessionDao
import ru.vo1d.web.entities.daybook.timetable.session.TimetableSession
import ru.vo1d.web.exposed.dao.XpDao
import ru.vo1d.web.exposed.entities.daybook.timetable.TimetableRegularSessions

class TimetableRegularSessionDaoXp : TimetableRegularSessionDao, XpDao<TimetableSession> {
    override suspend fun create(item: TimetableSession) {
        TimetableRegularSessions.insertIgnore { it.mapItem(item) }
    }

    override suspend fun create(vararg items: TimetableSession) =
        TimetableRegularSessions.batchInsert(items.asIterable(), ignore = true) { mapItem(it) }.count()


    override suspend fun delete(id: TimetableSession) =
        TimetableRegularSessions.deleteWhere {
            (sessionId eq id.sessionId) and (timetableId eq id.timetableId)
        }

    override fun UpdateBuilder<*>.mapItem(item: TimetableSession) {
        this[TimetableRegularSessions.timetableId] = item.timetableId!!
        this[TimetableRegularSessions.sessionId] = item.sessionId
    }
}