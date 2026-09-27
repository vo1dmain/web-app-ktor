package ru.vo1d.web.persistence.dao.daybook.timetable.dated

import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.core.inList
import org.jetbrains.exposed.v1.jdbc.*
import ru.vo1d.web.domain.dao.DatedSessionDao
import ru.vo1d.web.domain.filters.daybook.DatedSessionFilters
import ru.vo1d.web.domain.daybook.timetable.session.DatedSession
import ru.vo1d.web.persistence.context.DbContext
import ru.vo1d.web.persistence.dao.XpDao
import ru.vo1d.web.persistence.entities.daybook.timetable.DatedSessionEntity
import ru.vo1d.web.persistence.entities.daybook.timetable.DatedSessions
import ru.vo1d.web.persistence.entities.daybook.timetable.TimetableDatedSessions
import ru.vo1d.web.persistence.mappers.mapItem
import ru.vo1d.web.persistence.mappers.toDomain

class DatedSessionDaoXp(ctx: DbContext) : XpDao(ctx.daybook), DatedSessionDao {
    override suspend fun create(item: DatedSession): Int? = query {
        DatedSessions.insertIgnoreAndGetId { it.mapItem(item) }?.value
    }

    override suspend fun create(vararg items: DatedSession): Int = query {
        DatedSessions.batchInsert(items.asIterable(), ignore = true) { mapItem(it) }.count()
    }

    override suspend fun read(id: Int): DatedSession? = query {
        DatedSessionEntity.findById(id)?.toDomain()
    }

    override suspend fun update(item: DatedSession): Int = query {
        DatedSessions.update({ DatedSessions.id eq item.id }) { it.mapItem(item) }
    }

    override suspend fun delete(vararg items: DatedSession): Int = query {
        DatedSessions.deleteWhere { DatedSessions.id inList items.mapNotNull { it.id } }
    }

    override suspend fun page(offset: Long, limit: Int): List<DatedSession> = query {
        DatedSessionEntity.all()
            .limit(limit)
            .offset(offset)
            .sortedBy { it.dateTime }
            .map(DatedSessionEntity::toDomain)
    }

    override suspend fun filter(filters: DatedSessionFilters, offset: Long, limit: Int): List<DatedSession> {
        if (filters == DatedSessionFilters.Empty)
            return page(offset, limit)

        return query {
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
                filters.dateTime?.let {
                    andWhere { DatedSessions.dateTime eq it }
                }
                limit(limit)
                offset(offset)
            }

            DatedSessionEntity.wrapRows(query)
                .map(DatedSessionEntity::toDomain)
        }
    }
}
