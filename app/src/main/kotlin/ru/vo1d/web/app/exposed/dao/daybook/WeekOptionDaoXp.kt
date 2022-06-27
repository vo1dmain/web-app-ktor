package ru.vo1d.web.app.exposed.dao.daybook

import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.update
import ru.vo1d.web.app.data.dao.WeekOptionDao
import ru.vo1d.web.app.exposed.orm.daybook.timetable.WeekOption
import ru.vo1d.web.app.exposed.orm.daybook.timetable.WeekOptions
import ru.vo1d.web.entities.daybook.timetable.week.WeekOptionModel

class WeekOptionDaoXp : WeekOptionDao {
    override suspend fun create(item: WeekOptionModel): Int {
        return WeekOptions.insertAndGetId {
            it[description] = item.description
        }.value
    }

    override suspend fun read(id: Int): WeekOptionModel? {
        return WeekOption.findById(id)?.toModel()
    }

    override suspend fun update(item: WeekOptionModel): Int {
        return WeekOptions.update({ WeekOptions.id eq item.id }) {
            it[description] = item.description
        }
    }

    override suspend fun delete(id: Int): Int {
        return WeekOptions.deleteWhere {
            WeekOptions.id eq id
        }
    }

    override suspend fun all(): List<WeekOptionModel> {
        return WeekOption.all().map(WeekOption::toModel)
    }
}