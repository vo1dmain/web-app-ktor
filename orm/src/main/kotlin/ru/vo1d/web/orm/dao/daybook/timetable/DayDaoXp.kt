package ru.vo1d.web.orm.dao.daybook.timetable

import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.update
import ru.vo1d.web.data.dao.DayDao
import ru.vo1d.web.entities.daybook.timetable.day.DayModel
import ru.vo1d.web.orm.dao.XpDao
import ru.vo1d.web.orm.entities.daybook.timetable.Day
import ru.vo1d.web.orm.entities.daybook.timetable.Days

class DayDaoXp : DayDao, XpDao<DayModel> {
    override suspend fun create(item: DayModel) = Days.insertAndGetId { it.mapItem(item) }.value

    override suspend fun create(vararg items: DayModel) =
        Days.batchInsert(items.asIterable()) { mapItem(it) }.count()

    override suspend fun read(id: Int) = Day.findById(id)?.toModel()

    override suspend fun update(item: DayModel) = Days.update({ Days.id eq item.id }) { it.mapItem(item) }

    override suspend fun delete(id: Int) = Days.deleteWhere { Days.id eq id }

    override fun UpdateBuilder<Int>.mapItem(item: DayModel) {
        this[Days.title] = item.title
    }
}