package ru.vo1d.web.exposed.dao.daybook.timetable.regular

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import ru.vo1d.web.data.dao.RegularSessionDao
import ru.vo1d.web.data.filters.daybook.RegularSessionFilters
import ru.vo1d.web.entities.daybook.timetable.session.RegularSession
import ru.vo1d.web.exposed.entities.daybook.timetable.RegularSessionEntity
import ru.vo1d.web.exposed.entities.daybook.timetable.RegularSessions
import ru.vo1d.web.exposed.entities.daybook.timetable.TimetableRegularSessions
import ru.vo1d.web.exposed.mappers.mapItem
import ru.vo1d.web.exposed.mappers.toDomain

class RegularSessionDaoXp : RegularSessionDao {
    override suspend fun create(item: RegularSession): Int? {
        return RegularSessions.insertIgnoreAndGetId { it.mapItem(item) }?.value
    }

    override suspend fun create(vararg items: RegularSession): Int {
        return RegularSessions.batchInsert(items.asIterable(), ignore = true) { mapItem(it) }.count()
    }

    override suspend fun read(id: Int): RegularSession? {
        return RegularSessionEntity.findById(id)?.toDomain()
    }

    override suspend fun update(item: RegularSession): Int {
        return RegularSessions.update({ RegularSessions.id eq item.id }) { it.mapItem(item) }
    }

    override suspend fun delete(vararg items: RegularSession): Int {
        return RegularSessions.deleteWhere { RegularSessions.id eq id }
    }

    override suspend fun page(offset: Long, limit: Int): List<RegularSession> {
        return RegularSessionEntity.all()
            .limit(limit)
            .offset(offset)
            .sortedWith(compareBy({ it.dayOfWeek }, { it.timeId }))
            .map(RegularSessionEntity::toDomain)
    }

    override suspend fun filter(filters: RegularSessionFilters, offset: Long, limit: Int): List<RegularSession> {
        if (filters == RegularSessionFilters.Empty)
            return page(offset, limit)

        val query = RegularSessions.selectAll().apply {
            filters.timetableId?.let {
                adjustColumnSet { innerJoin(TimetableRegularSessions) }
                andWhere { TimetableRegularSessions.timetableId eq it }
            }
            filters.subject?.let {
                andWhere { RegularSessions.subject eq it }
            }
            filters.instructor?.let {
                andWhere { RegularSessions.instructor eq it }
            }
            filters.place?.let {
                andWhere { RegularSessions.place eq it }
            }
            filters.dayOfWeek?.let {
                andWhere { RegularSessions.dayOfWeek eq it }
            }
            filters.timeId?.let {
                andWhere { RegularSessions.timeId eq it }
            }
            filters.typeId?.let {
                andWhere { RegularSessions.typeId eq it }
            }
            filters.weekOption?.let {
                andWhere { RegularSessions.weekOption eq it }
            }
            limit(limit)
            offset(offset)
        }

        return RegularSessionEntity.wrapRows(query)
            .map(RegularSessionEntity::toDomain)
    }
}