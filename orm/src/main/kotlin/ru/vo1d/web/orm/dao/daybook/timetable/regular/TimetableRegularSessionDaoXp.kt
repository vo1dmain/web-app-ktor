package ru.vo1d.web.orm.dao.daybook.timetable.regular

import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import ru.vo1d.web.data.dao.TimetableRegularSessionDao
import ru.vo1d.web.entities.daybook.timetable.session.TimetableSessionModel
import ru.vo1d.web.orm.dao.XpDao
import ru.vo1d.web.orm.entities.daybook.timetable.TimetableRegularSessions

class TimetableRegularSessionDaoXp : TimetableRegularSessionDao, XpDao<TimetableSessionModel> {
    override suspend fun create(item: TimetableSessionModel) {
        TimetableRegularSessions.insert { it.mapItem(item) }
    }

    override suspend fun create(vararg items: TimetableSessionModel) =
        TimetableRegularSessions.batchInsert(items.asIterable()) { mapItem(it) }.count()


    override suspend fun delete(id: TimetableSessionModel) = TimetableRegularSessions.deleteWhere {
        (TimetableRegularSessions.sessionId eq id.sessionId) and (TimetableRegularSessions.timetableId eq id.timetableId)
    }

    override fun UpdateBuilder<Int>.mapItem(item: TimetableSessionModel) {
        this[TimetableRegularSessions.timetableId] = item.timetableId!!
        this[TimetableRegularSessions.sessionId] = item.sessionId
    }
}