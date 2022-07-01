package ru.vo1d.web.orm.dao.daybook.timetable

import org.jetbrains.exposed.sql.*
import ru.vo1d.web.data.dao.TimetableDao
import ru.vo1d.web.data.filters.daybook.TimetableFilters
import ru.vo1d.web.entities.daybook.timetable.TimetableModel
import ru.vo1d.web.orm.entities.daybook.timetable.Timetable
import ru.vo1d.web.orm.entities.daybook.timetable.TimetableWithData
import ru.vo1d.web.orm.entities.daybook.timetable.Timetables

class TimetableDaoXp : TimetableDao {
    override suspend fun create(item: TimetableModel) = Timetables.insertAndGetId {
        it[groupCode] = item.groupCode
        it[typeId] = item.typeId
    }.value

    override suspend fun read(id: Int) = TimetableWithData.findById(id)?.toDto()

    override suspend fun update(item: TimetableModel) = Timetables.update({ Timetables.id eq item.id }) {
        it[groupCode] = item.groupCode
        it[typeId] = item.typeId
    }

    override suspend fun delete(id: Int) = Timetables.deleteWhere { Timetables.id eq id }

    override suspend fun list(offset: Long, limit: Int) = Timetable.all().limit(limit, offset).map(Timetable::toModel)

    override suspend fun filter(filters: TimetableFilters, offset: Long, limit: Int): List<TimetableModel> {
        if (filters.areEmpty()) return list(offset, limit)

        val query = Timetables.selectAll()
        filters.typeId?.let {
            query.andWhere { Timetables.typeId eq it }
        }
        filters.groupCode?.let {
            query.andWhere { Timetables.groupCode eq it }
        }

        return Timetable.wrapRows(query).limit(limit, offset).map(Timetable::toModel)
    }
}