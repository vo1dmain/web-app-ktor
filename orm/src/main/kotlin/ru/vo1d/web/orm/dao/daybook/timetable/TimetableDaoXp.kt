package ru.vo1d.web.orm.dao.daybook.timetable

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import ru.vo1d.web.data.dao.TimetableDao
import ru.vo1d.web.data.filters.daybook.TimetableFilters
import ru.vo1d.web.entities.daybook.timetable.TimetableModel
import ru.vo1d.web.orm.dao.XpDao
import ru.vo1d.web.orm.entities.daybook.timetable.Timetable
import ru.vo1d.web.orm.entities.daybook.timetable.TimetableWithData
import ru.vo1d.web.orm.entities.daybook.timetable.Timetables

class TimetableDaoXp : TimetableDao, XpDao<TimetableModel> {
    override suspend fun create(item: TimetableModel) = Timetables.insertAndGetId { it.mapItem(item) }.value

    override suspend fun create(vararg items: TimetableModel) =
        Timetables.batchInsert(items.asIterable()) { mapItem(it) }.count()

    override suspend fun read(id: Int) = TimetableWithData.findById(id)?.toDto()

    override suspend fun update(item: TimetableModel) =
        Timetables.update({ Timetables.id eq item.id }) { it.mapItem(item) }

    override suspend fun delete(id: Int) = Timetables.deleteWhere { Timetables.id eq id }

    override suspend fun list(offset: Long, limit: Int) = Timetable.all().limit(limit, offset).map(Timetable::toModel)

    override suspend fun filter(filters: TimetableFilters, offset: Long, limit: Int): List<TimetableModel> {
        if (filters.areEmpty()) return list(offset, limit)

        val query = Timetables.selectAll().apply {
            filters.typeId?.let { andWhere { Timetables.typeId eq it } }
            filters.groupCode?.let { andWhere { Timetables.groupCode eq it } }
        }

        return Timetable.wrapRows(query).limit(limit, offset).map(Timetable::toModel)
    }

    override fun UpdateBuilder<Int>.mapItem(item: TimetableModel) {
        this[Timetables.groupCode] = item.groupCode
        this[Timetables.typeId] = item.typeId
    }
}