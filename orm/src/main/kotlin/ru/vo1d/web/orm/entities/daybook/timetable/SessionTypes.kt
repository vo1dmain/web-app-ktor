package ru.vo1d.web.orm.entities.daybook.timetable

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import ru.vo1d.web.entities.daybook.timetable.session.SessionTypeModel
import ru.vo1d.web.orm.entities.HasModel

object SessionTypes : IntIdTable() {
    val title = varchar("title", 32)
}

class SessionType(id: EntityID<Int>) : IntEntity(id), HasModel<SessionTypeModel> {
    companion object : IntEntityClass<SessionType>(SessionTypes)

    val title by SessionTypes.title

    override fun toModel() = SessionTypeModel(id.value, title)
}