package ru.vo1d.web.exposed.entities.daybook.group

import org.jetbrains.exposed.dao.id.EntityID
import ru.vo1d.web.exposed.tables.StringEntity
import ru.vo1d.web.exposed.tables.StringEntityClass
import ru.vo1d.web.exposed.tables.StringIdTable

internal object TableTypes : StringIdTable(idColumnLength = 8) {
    val title = varchar("title", 32)
}

internal class TableTypeEntity(id: EntityID<String>) : StringEntity(id) {
    companion object : StringEntityClass<TableTypeEntity>(TableTypes)

    val title by TableTypes.title
}