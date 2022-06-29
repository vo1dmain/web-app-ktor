package ru.vo1d.web.app.exposed.dao.daybook.timetable

import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.update
import ru.vo1d.web.app.data.dao.TimePeriodDao
import ru.vo1d.web.app.exposed.orm.daybook.timetable.TimePeriod
import ru.vo1d.web.app.exposed.orm.daybook.timetable.TimePeriods
import ru.vo1d.web.entities.daybook.timetable.period.TimePeriodModel

class TimePeriodDaoXp : TimePeriodDao {
    override suspend fun create(item: TimePeriodModel) = TimePeriods.insertAndGetId {
        it[start] = item.start
        it[end] = item.end
    }.value

    override suspend fun read(id: Int) = TimePeriod.findById(id)?.toModel()

    override suspend fun update(item: TimePeriodModel) = TimePeriods.update({ TimePeriods.id eq item.id }) {
        it[start] = item.start
        it[end] = item.end
    }

    override suspend fun delete(id: Int) = TimePeriods.deleteWhere { TimePeriods.id eq id }

    override suspend fun all() = TimePeriod.all().map(TimePeriod::toModel)
}