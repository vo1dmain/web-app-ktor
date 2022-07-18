package ru.vo1d.web.orm.dao.daybook.timetable

import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.update
import ru.vo1d.web.data.dao.TimePeriodDao
import ru.vo1d.web.entities.daybook.timetable.period.TimePeriodModel
import ru.vo1d.web.orm.dao.XpDao
import ru.vo1d.web.orm.entities.daybook.timetable.TimePeriod
import ru.vo1d.web.orm.entities.daybook.timetable.TimePeriods

class TimePeriodDaoXp : TimePeriodDao, XpDao<TimePeriodModel> {
    override suspend fun create(item: TimePeriodModel) = TimePeriods.insertAndGetId { it.mapItem(item) }.value

    override suspend fun create(vararg items: TimePeriodModel) =
        TimePeriods.batchInsert(items.asIterable()) { mapItem(it) }.count()

    override suspend fun read(id: Int) = TimePeriod.findById(id)?.toModel()

    override suspend fun update(item: TimePeriodModel) =
        TimePeriods.update({ TimePeriods.id eq item.id }) { it.mapItem(item) }

    override suspend fun delete(id: Int) = TimePeriods.deleteWhere { TimePeriods.id eq id }

    override suspend fun all() = TimePeriod.all().map(TimePeriod::toModel)

    override fun UpdateBuilder<Int>.mapItem(item: TimePeriodModel) {
        this[TimePeriods.start] = item.start
        this[TimePeriods.end] = item.end
        item.timeZone?.let { this[TimePeriods.timeZone] = it.id }
    }
}