package ru.vo1d.web.orm.entities.daybook.group

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import ru.vo1d.web.orm.entities.HasModel
import ru.vo1d.web.entities.daybook.group.type.TableType

object TableTypes : IdTable<String>() {
    override val id = varchar("id", 8).uniqueIndex().entityId()

    val title = varchar("title", 32)
}

class TableTypeEntity(id: EntityID<String>) : Entity<String>(id), HasModel<TableType> {
    companion object : EntityClass<String, TableTypeEntity>(TableTypes)

    val title by TableTypes.title

    override fun toModel() = TableType(id.value, title)
}