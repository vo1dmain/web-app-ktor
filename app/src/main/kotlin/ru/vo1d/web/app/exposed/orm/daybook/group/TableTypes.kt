package ru.vo1d.web.app.exposed.orm.daybook.group

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import ru.vo1d.web.app.exposed.orm.HasModel
import ru.vo1d.web.entities.daybook.group.type.TableTypeModel

object TableTypes : IdTable<String>() {
    override val id = varchar("id", 8).entityId()

    val title = varchar("title", 32)
}

class TableType(id: EntityID<String>) : Entity<String>(id), HasModel<TableTypeModel> {
    companion object : EntityClass<String, TableType>(TableTypes)

    val title by TableTypes.title

    override fun toModel() = TableTypeModel(id.value, title)
}