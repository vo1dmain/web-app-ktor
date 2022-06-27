package ru.penzgtu.web.app.exposed.orm.timetables

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import ru.penzgtu.web.app.exposed.orm.HasModel
import ru.penzgtu.web.entities.timetables.meta.parts.SessionTypeModel

object SessionTypes : IntIdTable() {
    val title = varchar("title", 16)
}

class SessionType(id: EntityID<Int>) : IntEntity(id), HasModel<SessionTypeModel> {
    companion object : IntEntityClass<SessionType>(SessionTypes)

    val title by SessionTypes.title

    override fun model() = SessionTypeModel(id.value, title)
}