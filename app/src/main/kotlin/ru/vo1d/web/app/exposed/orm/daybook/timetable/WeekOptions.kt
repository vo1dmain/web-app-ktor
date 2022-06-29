package ru.vo1d.web.app.exposed.orm.daybook.timetable

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import ru.vo1d.web.app.exposed.orm.HasModel
import ru.vo1d.web.entities.daybook.timetable.week.WeekOptionModel

object WeekOptions : IntIdTable() {
    val title = varchar("title", 16)
}

class WeekOption(id: EntityID<Int>) : IntEntity(id), HasModel<WeekOptionModel> {
    companion object : IntEntityClass<WeekOption>(WeekOptions)

    val title by WeekOptions.title

    override fun toModel() = WeekOptionModel(id.value, title)
}