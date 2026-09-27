package ru.vo1d.web.persistence.dao.daybook.timetable.dated

import org.jetbrains.exposed.v1.core.and
import org.jetbrains.exposed.v1.core.inList
import org.jetbrains.exposed.v1.core.statements.UpdateBuilder
import org.jetbrains.exposed.v1.jdbc.batchInsert
import org.jetbrains.exposed.v1.jdbc.deleteWhere
import org.jetbrains.exposed.v1.jdbc.insertIgnore
import ru.vo1d.web.domain.dao.TimetableDatedSessionDao
import ru.vo1d.web.domain.daybook.timetable.session.TimetableSession
import ru.vo1d.web.persistence.context.DbContext
import ru.vo1d.web.persistence.dao.XpDao
import ru.vo1d.web.persistence.entities.daybook.timetable.TimetableDatedSessions

class TimetableDatedSessionDaoXp(ctx: DbContext) : XpDao(ctx.daybook), TimetableDatedSessionDao {
    override suspend fun create(item: TimetableSession) = query {
        TimetableDatedSessions.insertIgnore { it.mapItem(item) }
        Unit
    }

    override suspend fun create(vararg items: TimetableSession): Int = query {
        TimetableDatedSessions.batchInsert(items.asIterable(), ignore = true) { mapItem(it) }.count()
    }

    override suspend fun read(id: Unit): TimetableSession {
        TODO("Not yet implemented")
    }

    override suspend fun update(item: TimetableSession): Int {
        TODO("Not yet implemented")
    }

    override suspend fun delete(vararg items: TimetableSession): Int = query {
        val sessionIds = items.map { it.sessionId }
        val timetableIds = items.map { it.timetableId }
        TimetableDatedSessions.deleteWhere {
            (sessionId inList sessionIds) and (timetableId inList timetableIds)
        }
    }
}

private fun UpdateBuilder<*>.mapItem(item: TimetableSession) {
    this[TimetableDatedSessions.timetableId] = item.timetableId
    this[TimetableDatedSessions.sessionId] = item.sessionId
}
