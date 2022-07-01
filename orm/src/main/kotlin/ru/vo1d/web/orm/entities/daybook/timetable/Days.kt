package ru.vo1d.web.orm.entities.daybook.timetable

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import ru.vo1d.web.entities.daybook.timetable.day.DayModel
import ru.vo1d.web.orm.entities.HasModel

object Days : IntIdTable() {
    val title = varchar("title", 16)
}

class Day(id: EntityID<Int>) : IntEntity(id), HasModel<DayModel> {
    companion object : IntEntityClass<Day>(Days)

    val title by Days.title

    override fun toModel() = DayModel(id.value, title)
}