package ru.vo1d.web.exposed.dao.daybook.timetable

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.inList
import ru.vo1d.web.data.dao.TimetableDao
import ru.vo1d.web.data.filters.daybook.TimetableFilters
import ru.vo1d.web.entities.daybook.timetable.Timetable
import ru.vo1d.web.exposed.entities.daybook.timetable.TimetableEntity
import ru.vo1d.web.exposed.entities.daybook.timetable.Timetables
import ru.vo1d.web.exposed.mappers.mapItem
import ru.vo1d.web.exposed.mappers.toDomain

class TimetableDaoXp : TimetableDao {
    override suspend fun create(item: Timetable): Int? {
        return Timetables.insertIgnoreAndGetId { it.mapItem(item) }?.value
    }

    override suspend fun create(vararg items: Timetable): Int {
        return Timetables.batchInsert(items.asIterable(), ignore = true) { mapItem(it) }.count()
    }

    override suspend fun read(id: Int): Timetable? {
        return TimetableEntity.findById(id)?.toDomain()
    }

    override suspend fun update(item: Timetable): Int {
        return Timetables.update({ Timetables.id eq item.id }) { it.mapItem(item) }
    }

    override suspend fun delete(vararg items: Timetable): Int {
        return Timetables.deleteWhere { Timetables.id inList items.mapNotNull { it.id } }
    }

    override suspend fun page(offset: Long, limit: Int): List<Timetable> {
        return TimetableEntity.all()
            .limit(limit)
            .offset(offset)
            .map(TimetableEntity::toDomain)
    }

    override suspend fun filter(filters: TimetableFilters, offset: Long, limit: Int): List<Timetable> {
        if (filters == TimetableFilters.Empty)
            return page(offset, limit)

        val query = Timetables.selectAll().apply {
            filters.typeId?.let { andWhere { Timetables.typeId eq it } }
            filters.groupCode?.let { andWhere { Timetables.groupCode like it } }
            filters.format?.let { andWhere { Timetables.format eq it } }
            orderBy(Timetables.id)
            limit(limit)
            offset(offset)
        }

        return TimetableEntity.wrapRows(query)
            .map(TimetableEntity::toDomain)
    }
}