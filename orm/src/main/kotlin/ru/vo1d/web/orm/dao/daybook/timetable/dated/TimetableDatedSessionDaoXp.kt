package ru.vo1d.web.orm.dao.daybook.timetable.dated

import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import ru.vo1d.web.data.dao.TimetableDatedSessionDao
import ru.vo1d.web.entities.daybook.timetable.session.TimetableSessionModel
import ru.vo1d.web.orm.dao.XpDao
import ru.vo1d.web.orm.entities.daybook.timetable.TimetableDatedSessions

class TimetableDatedSessionDaoXp : TimetableDatedSessionDao, XpDao<TimetableSessionModel> {
    override suspend fun create(item: TimetableSessionModel) {
        TimetableDatedSessions.insert { it.mapItem(item) }
    }

    override suspend fun create(vararg items: TimetableSessionModel) =
        TimetableDatedSessions.batchInsert(items.asIterable()) { mapItem(it) }.count()


    override suspend fun delete(id: TimetableSessionModel) = TimetableDatedSessions.deleteWhere {
        (TimetableDatedSessions.sessionId eq id.sessionId) and (TimetableDatedSessions.timetableId eq id.timetableId)
    }

    override fun UpdateBuilder<Int>.mapItem(item: TimetableSessionModel) {
        this[TimetableDatedSessions.timetableId] = item.timetableId!!
        this[TimetableDatedSessions.sessionId] = item.sessionId
    }
}