package ru.vo1d.web.exposed.entities.daybook.timetable

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import ru.vo1d.web.entities.daybook.timetable.session.SessionType
import ru.vo1d.web.exposed.entities.HasModel

object SessionTypes : IntIdTable() {
    val title = varchar("title", 32).uniqueIndex()
}

class SessionTypeEntity(id: EntityID<Int>) : IntEntity(id), HasModel<SessionType> {
    companion object : IntEntityClass<SessionTypeEntity>(SessionTypes)

    val title by SessionTypes.title

    override fun toModel() = SessionType(id.value, title)
}