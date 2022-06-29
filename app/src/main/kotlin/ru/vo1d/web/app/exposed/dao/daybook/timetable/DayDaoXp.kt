package ru.vo1d.web.app.exposed.dao.daybook.timetable

import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.update
import ru.vo1d.web.app.data.dao.DayDao
import ru.vo1d.web.app.exposed.orm.daybook.timetable.Day
import ru.vo1d.web.app.exposed.orm.daybook.timetable.Days
import ru.vo1d.web.entities.daybook.timetable.day.DayModel

class DayDaoXp : DayDao {
    override suspend fun create(item: DayModel) = Days.insertAndGetId {
        it[title] = item.title
    }.value

    override suspend fun read(id: Int) = Day.findById(id)?.toModel()

    override suspend fun update(item: DayModel) = Days.update({ Days.id eq item.id }) {
        it[title] = item.title
    }

    override suspend fun delete(id: Int) = Days.deleteWhere { Days.id eq id }
}