package ru.vo1d.web.exposed.utils.tables

import org.jetbrains.exposed.dao.id.IdTable

abstract class StringIdTable(
    name: String = "",
    idColName: String = "id",
    idColLength: Int,
    collate: String? = null
) : IdTable<String>(name) {
    final override val id = varchar(idColName, idColLength, collate).uniqueIndex().entityId()
    final override val primaryKey = PrimaryKey(id)
}