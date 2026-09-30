package ru.vo1d.web.persistence.daybook

import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass

internal object SessionTypes : IntIdTable() {
    val title = varchar("title", 32).uniqueIndex()
}

internal class SessionTypeEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<SessionTypeEntity>(SessionTypes)

    val title by SessionTypes.title
}