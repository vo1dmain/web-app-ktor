package ru.vo1d.web.exposed.dao.daybook.timetable

import org.jetbrains.exposed.sql.SqlExpressionBuilder.inList
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertIgnoreAndGetId
import org.jetbrains.exposed.sql.update
import ru.vo1d.web.data.dao.StartTimeDao
import ru.vo1d.web.entities.daybook.timetable.time.StartTime
import ru.vo1d.web.exposed.entities.daybook.timetable.SessionStartTimes
import ru.vo1d.web.exposed.entities.daybook.timetable.StartTimeEntity
import ru.vo1d.web.exposed.mappers.mapItem
import ru.vo1d.web.exposed.mappers.toDomain

class StartTimeDaoXp : StartTimeDao {
    override suspend fun create(item: StartTime): Int? {
        return SessionStartTimes.insertIgnoreAndGetId { it.mapItem(item) }?.value
    }

    override suspend fun create(vararg items: StartTime): Int {
        return SessionStartTimes.batchInsert(items.asIterable(), ignore = true) { mapItem(it) }.count()
    }

    override suspend fun read(id: Int): StartTime? {
        return StartTimeEntity.findById(id)?.toDomain()
    }

    override suspend fun update(item: StartTime): Int {
        return SessionStartTimes.update({ SessionStartTimes.id eq item.id }) { it.mapItem(item) }
    }

    override suspend fun delete(vararg items: StartTime): Int {
        return SessionStartTimes.deleteWhere { SessionStartTimes.id inList items.mapNotNull { it.id } }
    }

    override suspend fun all(): List<StartTime> {
        return StartTimeEntity.all().map(StartTimeEntity::toDomain)
    }
}