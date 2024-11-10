package ru.vo1d.web.exposed.tables

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable

abstract class StringIdTable(
    name: String = "",
    idColumnName: String = "id",
    idColumnLength: Int,
    collate: String? = null
) : IdTable<String>(name) {
    final override val id = varchar(idColumnName, idColumnLength, collate).uniqueIndex().entityId()
    final override val primaryKey = PrimaryKey(id)
}

abstract class StringEntity(id: EntityID<String>) : Entity<String>(id)

abstract class StringEntityClass<out E : StringEntity>(
    table: IdTable<String>,
    entityType: Class<E>? = null,
    entityCtor: ((EntityID<String>) -> E)? = null
) : EntityClass<String, E>(table, entityType, entityCtor)