package ru.vo1d.web.orm.dao.daybook.timetable

import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import ru.vo1d.web.data.dao.TimetableSessionDao
import ru.vo1d.web.entities.daybook.timetable.session.TimetableSessionModel
import ru.vo1d.web.orm.dao.XpDao
import ru.vo1d.web.orm.entities.daybook.timetable.TimetableSessions

class TimetableSessionDaoXp : TimetableSessionDao, XpDao<TimetableSessionModel> {
    override suspend fun create(item: TimetableSessionModel) {
        TimetableSessions.insert { it.mapItem(item) }
    }

    override suspend fun create(vararg items: TimetableSessionModel) =
        TimetableSessions.batchInsert(items.asIterable()) { mapItem(it) }.count()

    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override suspend fun delete(item: TimetableSessionModel) = TimetableSessions.deleteWhere {
        (TimetableSessions.sessionId eq item.sessionId) and (TimetableSessions.timetableId eq item.timetableId)
    }

    override fun UpdateBuilder<Int>.mapItem(item: TimetableSessionModel) {
        this[TimetableSessions.timetableId] = item.timetableId!!
        this[TimetableSessions.sessionId] = item.sessionId
    }
}