package ru.vo1d.web.exposed.dao.daybook.timetable.dated

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.inList
import ru.vo1d.web.data.dao.DatedSessionDao
import ru.vo1d.web.data.filters.daybook.DatedSessionFilters
import ru.vo1d.web.entities.daybook.timetable.session.DatedSession
import ru.vo1d.web.exposed.entities.daybook.timetable.DatedSessionEntity
import ru.vo1d.web.exposed.entities.daybook.timetable.DatedSessions
import ru.vo1d.web.exposed.entities.daybook.timetable.TimetableDatedSessions
import ru.vo1d.web.exposed.mappers.mapItem
import ru.vo1d.web.exposed.mappers.toDomain

class DatedSessionDaoXp : DatedSessionDao {
    override suspend fun create(item: DatedSession): Int? {
        return DatedSessions.insertIgnoreAndGetId { it.mapItem(item) }?.value
    }

    override suspend fun create(vararg items: DatedSession): Int {
        return DatedSessions.batchInsert(items.asIterable(), ignore = true) { mapItem(it) }.count()
    }

    override suspend fun read(id: Int): DatedSession? {
        return DatedSessionEntity.findById(id)?.toDomain()
    }

    override suspend fun update(item: DatedSession): Int {
        return DatedSessions.update({ DatedSessions.id eq item.id }) { it.mapItem(item) }
    }

    override suspend fun delete(vararg items: DatedSession): Int {
        return DatedSessions.deleteWhere { DatedSessions.id inList items.mapNotNull { it.id } }
    }

    override suspend fun page(offset: Long, limit: Int): List<DatedSession> {
        return DatedSessionEntity.all()
            .limit(limit)
            .offset(offset)
            .sortedBy { it.datetime }
            .map(DatedSessionEntity::toDomain)
    }

    override suspend fun filter(filters: DatedSessionFilters, offset: Long, limit: Int): List<DatedSession> {
        if (filters == DatedSessionFilters.Empty)
            return page(offset, limit)

        val query = DatedSessions.selectAll().apply {
            filters.timetableId?.let {
                adjustColumnSet { innerJoin(TimetableDatedSessions) }
                andWhere { TimetableDatedSessions.timetableId eq it }
            }
            filters.subject?.let {
                andWhere { DatedSessions.subject eq it }
            }
            filters.instructor?.let {
                andWhere { DatedSessions.instructor eq it }
            }
            filters.place?.let {
                andWhere { DatedSessions.place eq it }
            }
            filters.typeId?.let {
                andWhere { DatedSessions.typeId eq it }
            }
            filters.datetime?.let {
                andWhere { DatedSessions.datetime eq it }
            }
            limit(limit)
            offset(offset)
        }

        return DatedSessionEntity.wrapRows(query)
            .map(DatedSessionEntity::toDomain)
    }
}