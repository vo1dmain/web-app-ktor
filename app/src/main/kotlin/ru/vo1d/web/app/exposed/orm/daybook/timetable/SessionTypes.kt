package ru.vo1d.web.app.exposed.orm.daybook.timetable

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import ru.vo1d.web.app.exposed.orm.HasModel
import ru.vo1d.web.entities.daybook.timetable.session.SessionTypeModel

object SessionTypes : IntIdTable() {
    val title = varchar("title", 16)
}

class SessionType(id: EntityID<Int>) : IntEntity(id), HasModel<SessionTypeModel> {
    companion object : IntEntityClass<SessionType>(SessionTypes)

    val title by SessionTypes.title

    override fun toModel() = SessionTypeModel(id.value, title)
}