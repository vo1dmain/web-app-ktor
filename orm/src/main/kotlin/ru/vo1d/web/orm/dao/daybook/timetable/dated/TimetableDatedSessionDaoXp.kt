package ru.vo1d.web.orm.dao.daybook.timetable.dated

import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertIgnore
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import ru.vo1d.web.data.dao.TimetableDatedSessionDao
import ru.vo1d.web.entities.daybook.timetable.session.TimetableSession
import ru.vo1d.web.orm.dao.XpDao
import ru.vo1d.web.orm.entities.daybook.timetable.TimetableDatedSessions

class TimetableDatedSessionDaoXp : TimetableDatedSessionDao, XpDao<TimetableSession> {
    override suspend fun create(item: TimetableSession) {
        TimetableDatedSessions.insertIgnore { it.mapItem(item) }
    }

    override suspend fun create(vararg items: TimetableSession) =
        TimetableDatedSessions.batchInsert(items.asIterable(), ignore = true) { mapItem(it) }.count()


    override suspend fun delete(id: TimetableSession) =
        TimetableDatedSessions.deleteWhere {
            (TimetableDatedSessions.sessionId eq id.sessionId) and
                    (TimetableDatedSessions.timetableId eq id.timetableId)
        }

    override fun UpdateBuilder<*>.mapItem(item: TimetableSession) {
        this[TimetableDatedSessions.timetableId] = item.timetableId!!
        this[TimetableDatedSessions.sessionId] = item.sessionId
    }
}