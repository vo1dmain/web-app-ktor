package ru.penzgtu.web.app.exposed.orm.timetables

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import ru.penzgtu.web.app.exposed.orm.HasModel
import ru.penzgtu.web.entities.timetables.list.parts.DayModel

object Days : IntIdTable() {
    val title = varchar("title", 16)
}

class Day(id: EntityID<Int>) : IntEntity(id), HasModel<DayModel> {
    companion object : IntEntityClass<Day>(Days)

    val title by Days.title

    override fun model() = DayModel(id.value, title)
}