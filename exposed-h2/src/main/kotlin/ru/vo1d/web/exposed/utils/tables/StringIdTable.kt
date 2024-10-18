package ru.vo1d.web.exposed.utils.tables

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