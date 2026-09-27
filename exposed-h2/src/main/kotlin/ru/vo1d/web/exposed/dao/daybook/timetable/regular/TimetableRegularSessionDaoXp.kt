package ru.vo1d.web.exposed.dao.daybook.timetable.regular

import org.jetbrains.exposed.v1.core.and
import org.jetbrains.exposed.v1.core.inList
import org.jetbrains.exposed.v1.core.statements.UpdateBuilder
import org.jetbrains.exposed.v1.jdbc.batchInsert
import org.jetbrains.exposed.v1.jdbc.deleteWhere
import org.jetbrains.exposed.v1.jdbc.insertIgnore
import ru.vo1d.web.data.dao.TimetableRegularSessionDao
import ru.vo1d.web.entities.daybook.timetable.session.TimetableSession
import ru.vo1d.web.exposed.context.DbContext
import ru.vo1d.web.exposed.dao.XpDao
import ru.vo1d.web.exposed.entities.daybook.timetable.TimetableRegularSessions

class TimetableRegularSessionDaoXp(ctx: DbContext) : XpDao(ctx.daybook), TimetableRegularSessionDao {
    override suspend fun create(item: TimetableSession) = query {
        TimetableRegularSessions.insertIgnore { it.mapItem(item) }
        Unit
    }

    override suspend fun create(vararg items: TimetableSession): Int = query {
        TimetableRegularSessions.batchInsert(items.asIterable(), ignore = true) {
            mapItem(it)
        }.count()
    }

    override suspend fun read(id: Unit): TimetableSession {
        TODO("Not yet implemented")
    }

    override suspend fun update(item: TimetableSession): Int {
        TODO("Not yet implemented")
    }

    override suspend fun delete(vararg items: TimetableSession): Int = query {
        TimetableRegularSessions.deleteWhere {
            (sessionId inList items.map { it.sessionId }) and (timetableId inList items.map { it.timetableId })
        }
    }
}

private fun UpdateBuilder<*>.mapItem(item: TimetableSession) {
    this[TimetableRegularSessions.timetableId] = item.timetableId
    this[TimetableRegularSessions.sessionId] = item.sessionId
}
