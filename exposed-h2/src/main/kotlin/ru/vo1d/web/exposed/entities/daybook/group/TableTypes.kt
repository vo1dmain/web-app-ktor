package ru.vo1d.web.exposed.entities.daybook.group

import org.jetbrains.exposed.dao.id.EntityID
import ru.vo1d.web.entities.daybook.group.type.TableType
import ru.vo1d.web.exposed.entities.HasModel
import ru.vo1d.web.exposed.utils.tables.StringEntity
import ru.vo1d.web.exposed.utils.tables.StringEntityClass
import ru.vo1d.web.exposed.utils.tables.StringIdTable

object TableTypes : StringIdTable(idColLength = 8) {
    val title = varchar("title", 32)
}

class TableTypeEntity(id: EntityID<String>) : StringEntity(id), HasModel<TableType> {
    companion object : StringEntityClass<TableTypeEntity>(TableTypes)

    val title by TableTypes.title

    override fun toModel() = TableType(id.value, title)
}