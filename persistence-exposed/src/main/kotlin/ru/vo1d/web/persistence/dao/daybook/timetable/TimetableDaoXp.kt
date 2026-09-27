package ru.vo1d.web.persistence.dao.daybook.timetable

import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.core.inList
import org.jetbrains.exposed.v1.core.like
import org.jetbrains.exposed.v1.jdbc.*
import ru.vo1d.web.domain.dao.TimetableDao
import ru.vo1d.web.domain.filters.daybook.TimetableFilters
import ru.vo1d.web.domain.daybook.timetable.Timetable
import ru.vo1d.web.persistence.context.DbContext
import ru.vo1d.web.persistence.dao.XpDao
import ru.vo1d.web.persistence.entities.daybook.timetable.TimetableEntity
import ru.vo1d.web.persistence.entities.daybook.timetable.Timetables
import ru.vo1d.web.persistence.mappers.mapItem
import ru.vo1d.web.persistence.mappers.toDomain

class TimetableDaoXp(ctx: DbContext) : XpDao(ctx.daybook), TimetableDao {
    override suspend fun create(item: Timetable): Int? = query {
        Timetables.insertIgnoreAndGetId { it.mapItem(item) }?.value
    }

    override suspend fun create(vararg items: Timetable): Int = query {
        Timetables.batchInsert(items.asIterable(), ignore = true) { mapItem(it) }.count()
    }

    override suspend fun read(id: Int): Timetable? = query {
        TimetableEntity.findById(id)?.toDomain()
    }

    override suspend fun update(item: Timetable): Int = query {
        Timetables.update({ Timetables.id eq item.id }) { it.mapItem(item) }
    }

    override suspend fun delete(vararg items: Timetable): Int = query {
        Timetables.deleteWhere { Timetables.id inList items.mapNotNull { it.id } }
    }

    override suspend fun page(offset: Long, limit: Int): List<Timetable> = query {
        TimetableEntity.all()
            .limit(limit)
            .offset(offset)
            .map(TimetableEntity::toDomain)
    }

    override suspend fun filter(filters: TimetableFilters, offset: Long, limit: Int): List<Timetable> {
        if (filters == TimetableFilters.Empty)
            return page(offset, limit)

        return query {
            val query = Timetables.selectAll().apply {
                filters.typeId?.let { andWhere { Timetables.typeId eq it } }
                filters.groupCode?.let { andWhere { Timetables.groupCode like it } }
                filters.format?.let { andWhere { Timetables.format eq it } }
                orderBy(Timetables.id)
                limit(limit)
                offset(offset)
            }

            TimetableEntity.wrapRows(query)
                .map(TimetableEntity::toDomain)
        }
    }
}
