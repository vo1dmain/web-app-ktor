package ru.vo1d.web.orm.dao.daybook.timetable

import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.update
import ru.vo1d.web.data.dao.WeekOptionDao
import ru.vo1d.web.entities.daybook.timetable.week.WeekOptionModel
import ru.vo1d.web.orm.dao.XpDao
import ru.vo1d.web.orm.entities.daybook.timetable.WeekOption
import ru.vo1d.web.orm.entities.daybook.timetable.WeekOptions

class WeekOptionDaoXp : WeekOptionDao, XpDao<WeekOptionModel> {
    override suspend fun create(item: WeekOptionModel) = WeekOptions.insertAndGetId { it.mapItem(item) }.value

    override suspend fun create(vararg items: WeekOptionModel) =
        WeekOptions.batchInsert(items.asIterable()) { mapItem(it) }.count()

    override suspend fun read(id: Int) = WeekOption.findById(id)?.toModel()

    override suspend fun update(item: WeekOptionModel) =
        WeekOptions.update({ WeekOptions.id eq item.id }) { it.mapItem(item) }

    override suspend fun delete(id: Int) = WeekOptions.deleteWhere { WeekOptions.id eq id }

    override suspend fun all() = WeekOption.all().map(WeekOption::toModel)

    override fun UpdateBuilder<Int>.mapItem(item: WeekOptionModel) {
        this[WeekOptions.title] = item.title
    }
}