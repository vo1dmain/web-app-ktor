package ru.vo1d.web.exposed.dao.daybook.timetable

import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertIgnoreAndGetId
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.update
import ru.vo1d.web.data.dao.StartTimeDao
import ru.vo1d.web.entities.daybook.timetable.time.StartTime
import ru.vo1d.web.exposed.dao.XpDao
import ru.vo1d.web.exposed.entities.daybook.timetable.SessionStartTime
import ru.vo1d.web.exposed.entities.daybook.timetable.SessionStartTimes

class StartTimeDaoXp : StartTimeDao, XpDao<StartTime> {
    override suspend fun create(item: StartTime) =
        SessionStartTimes.insertIgnoreAndGetId { it.mapItem(item) }?.value

    override suspend fun create(vararg items: StartTime) =
        SessionStartTimes.batchInsert(items.asIterable(), ignore = true) { mapItem(it) }.count()

    override suspend fun read(id: Int) =
        SessionStartTime.findById(id)?.toModel()

    override suspend fun update(item: StartTime) =
        SessionStartTimes.update({ SessionStartTimes.id eq item.id }) { it.mapItem(item) }

    override suspend fun delete(id: Int) =
        SessionStartTimes.deleteWhere { SessionStartTimes.id eq id }

    override suspend fun all() =
        SessionStartTime.all().map(SessionStartTime::toModel)

    override fun UpdateBuilder<*>.mapItem(item: StartTime) {
        this[SessionStartTimes.time] = item.time
        item.timeZone?.let { this[SessionStartTimes.timeZone] = it.id }
    }
}