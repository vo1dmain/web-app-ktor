package ru.vo1d.web.exposed.entities.daybook.timetable

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

internal object SessionTypes : IntIdTable() {
    val title = varchar("title", 32).uniqueIndex()
}

internal class SessionTypeEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<SessionTypeEntity>(SessionTypes)

    val title by SessionTypes.title
}