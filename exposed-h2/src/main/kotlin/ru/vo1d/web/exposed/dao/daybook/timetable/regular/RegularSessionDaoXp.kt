package ru.vo1d.web.exposed.dao.daybook.timetable.regular

import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.core.inList
import org.jetbrains.exposed.v1.jdbc.*
import ru.vo1d.web.data.dao.RegularSessionDao
import ru.vo1d.web.data.filters.daybook.RegularSessionFilters
import ru.vo1d.web.entities.daybook.timetable.session.RegularSession
import ru.vo1d.web.exposed.context.DbContext
import ru.vo1d.web.exposed.dao.XpDao
import ru.vo1d.web.exposed.entities.daybook.timetable.RegularSessionEntity
import ru.vo1d.web.exposed.entities.daybook.timetable.RegularSessions
import ru.vo1d.web.exposed.entities.daybook.timetable.TimetableRegularSessions
import ru.vo1d.web.exposed.mappers.mapItem
import ru.vo1d.web.exposed.mappers.toDomain

class RegularSessionDaoXp(ctx: DbContext) : XpDao(ctx.daybook), RegularSessionDao {
    override suspend fun create(item: RegularSession): Int? = query {
        RegularSessions.insertIgnoreAndGetId { it.mapItem(item) }?.value
    }

    override suspend fun create(vararg items: RegularSession): Int = query {
        RegularSessions.batchInsert(items.asIterable(), ignore = true) { mapItem(it) }.count()
    }

    override suspend fun read(id: Int): RegularSession? = query {
        RegularSessionEntity.findById(id)?.toDomain()
    }

    override suspend fun update(item: RegularSession): Int = query {
        RegularSessions.update({ RegularSessions.id eq item.id }) { it.mapItem(item) }
    }

    override suspend fun delete(vararg items: RegularSession): Int = query {
        RegularSessions.deleteWhere { RegularSessions.id inList items.mapNotNull { it.id } }
    }

    override suspend fun page(offset: Long, limit: Int): List<RegularSession> = query {
        RegularSessionEntity.all()
            .limit(limit)
            .offset(offset)
            .sortedWith(compareBy({ it.dayOfWeek }, { it.time }))
            .map(RegularSessionEntity::toDomain)
    }

    override suspend fun filter(filters: RegularSessionFilters, offset: Long, limit: Int): List<RegularSession> {
        if (filters == RegularSessionFilters.Empty)
            return page(offset, limit)

        return query {
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
                filters.time?.let {
                    andWhere { RegularSessions.time eq it }
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

            RegularSessionEntity.wrapRows(query)
                .map(RegularSessionEntity::toDomain)
        }
    }
}
