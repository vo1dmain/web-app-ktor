package ru.vo1d.web.persistence.entities.daybook.group

import org.jetbrains.exposed.v1.core.dao.id.EntityID
import ru.vo1d.web.persistence.tables.StringEntity
import ru.vo1d.web.persistence.tables.StringEntityClass
import ru.vo1d.web.persistence.tables.StringIdTable

internal object TableTypes : StringIdTable(idColumnLength = 8) {
    val title = varchar("title", 32)
}

internal class TableTypeEntity(id: EntityID<String>) : StringEntity(id) {
    companion object : StringEntityClass<TableTypeEntity>(TableTypes)

    val title by TableTypes.title
}