package ru.vo1d.web.exposed.dao.daybook.timetable

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import ru.vo1d.web.data.dao.TimetableDao
import ru.vo1d.web.data.filters.daybook.TimetableFilters
import ru.vo1d.web.entities.daybook.timetable.Timetable
import ru.vo1d.web.exposed.dao.XpDao
import ru.vo1d.web.exposed.entities.daybook.timetable.TimetableEntity
import ru.vo1d.web.exposed.entities.daybook.timetable.TimetableWithSessionsEntity
import ru.vo1d.web.exposed.entities.daybook.timetable.Timetables

class TimetableDaoXp : TimetableDao, XpDao<Timetable> {
    override suspend fun create(item: Timetable) =
        Timetables.insertIgnoreAndGetId { it.mapItem(item) }?.value

    override suspend fun create(vararg items: Timetable) =
        Timetables.batchInsert(items.asIterable(), ignore = true) { mapItem(it) }.count()

    override suspend fun read(id: Int) =
        TimetableEntity.findById(id)?.toModel()

    override suspend fun readLinked(id: Int) =
        TimetableWithSessionsEntity.findById(id)?.toModel()

    override suspend fun update(item: Timetable) =
        Timetables.update({ Timetables.id eq item.id }) { it.mapItem(item) }

    override suspend fun delete(id: Int) =
        Timetables.deleteWhere { Timetables.id eq id }

    override suspend fun list(offset: Long, limit: Int) =
        TimetableEntity.all().limit(limit, offset).map(TimetableEntity::toModel)

    override suspend fun filter(filters: TimetableFilters, offset: Long, limit: Int): List<Timetable> {
        if (filters.areEmpty()) return list(offset, limit)

        val query = Timetables.selectAll().apply {
            filters.typeId?.let { andWhere { Timetables.typeId eq it } }
            filters.groupCode?.let { andWhere { Timetables.groupCode like it } }
            filters.format?.let { andWhere { Timetables.format eq it } }
            orderBy(Timetables.id)
        }

        return TimetableEntity.wrapRows(query).limit(limit, offset).map(TimetableEntity::toModel)
    }

    override fun UpdateBuilder<*>.mapItem(item: Timetable) {
        this[Timetables.groupCode] = item.groupCode
        this[Timetables.typeId] = item.typeId
        this[Timetables.format] = item.format
    }
}