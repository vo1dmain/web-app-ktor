package ru.vo1d.web.exposed.dao.daybook.timetable.dated

import org.jetbrains.exposed.v1.core.and
import org.jetbrains.exposed.v1.core.inList
import org.jetbrains.exposed.v1.core.statements.UpdateBuilder
import org.jetbrains.exposed.v1.jdbc.batchInsert
import org.jetbrains.exposed.v1.jdbc.deleteWhere
import org.jetbrains.exposed.v1.jdbc.insertIgnore
import ru.vo1d.web.data.dao.TimetableDatedSessionDao
import ru.vo1d.web.entities.daybook.timetable.session.TimetableSession
import ru.vo1d.web.exposed.entities.daybook.timetable.TimetableDatedSessions

class TimetableDatedSessionDaoXp : TimetableDatedSessionDao {
    override suspend fun create(item: TimetableSession) {
        TimetableDatedSessions.insertIgnore { it.mapItem(item) }
    }

    override suspend fun create(vararg items: TimetableSession): Int {
        return TimetableDatedSessions.batchInsert(items.asIterable(), ignore = true) { mapItem(it) }.count()
    }

    override suspend fun read(id: Unit): TimetableSession {
        TODO("Not yet implemented")
    }

    override suspend fun update(item: TimetableSession): Int {
        TODO("Not yet implemented")
    }

    override suspend fun delete(vararg items: TimetableSession): Int {
        val sessionIds = items.map { it.sessionId }
        val timetableIds = items.map { it.timetableId }
        return TimetableDatedSessions.deleteWhere {
            (sessionId inList sessionIds) and (timetableId inList timetableIds)
        }
    }
}

private fun UpdateBuilder<*>.mapItem(item: TimetableSession) {
    this[TimetableDatedSessions.timetableId] = item.timetableId
    this[TimetableDatedSessions.sessionId] = item.sessionId
}