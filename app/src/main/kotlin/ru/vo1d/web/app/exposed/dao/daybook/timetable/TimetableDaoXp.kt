package ru.vo1d.web.app.exposed.dao.daybook.timetable

import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.or
import org.jetbrains.exposed.sql.update
import ru.vo1d.web.app.data.dao.TimetableDao
import ru.vo1d.web.app.data.filters.daybook.TimetableFilters
import ru.vo1d.web.app.exposed.orm.daybook.timetable.Timetable
import ru.vo1d.web.app.exposed.orm.daybook.timetable.TimetableWithData
import ru.vo1d.web.app.exposed.orm.daybook.timetable.Timetables
import ru.vo1d.web.entities.daybook.timetable.TimetableModel

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

        return Timetable.find {
            (Timetables.typeId eq filters.typeId) or (Timetables.groupCode eq filters.groupCode)
        }.limit(limit, offset).map(Timetable::toModel)
    }
}