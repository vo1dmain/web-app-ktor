package ru.vo1d.web.orm.dao.daybook.timetable

import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.update
import ru.vo1d.web.data.dao.WeekOptionDao
import ru.vo1d.web.entities.daybook.timetable.week.WeekOptionModel
import ru.vo1d.web.orm.entities.daybook.timetable.WeekOption
import ru.vo1d.web.orm.entities.daybook.timetable.WeekOptions

class WeekOptionDaoXp : WeekOptionDao {
    override suspend fun create(item: WeekOptionModel) = WeekOptions.insertAndGetId {
        it[title] = item.title
    }.value

    override suspend fun read(id: Int) = WeekOption.findById(id)?.toModel()

    override suspend fun update(item: WeekOptionModel) = WeekOptions.update({ WeekOptions.id eq item.id }) {
        it[title] = item.title
    }

    override suspend fun delete(id: Int) = WeekOptions.deleteWhere { WeekOptions.id eq id }

    override suspend fun all() = WeekOption.all().map(WeekOption::toModel)
}