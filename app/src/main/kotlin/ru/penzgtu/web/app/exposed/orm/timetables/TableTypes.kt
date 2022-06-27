package ru.penzgtu.web.app.exposed.orm.timetables

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import ru.penzgtu.web.app.exposed.orm.HasModel
import ru.penzgtu.web.entities.timetables.meta.parts.TableTypeModel

object TableTypes : IntIdTable() {
    val title = varchar("title", 32)
}

class TableType(id: EntityID<Int>) : IntEntity(id), HasModel<TableTypeModel> {
    companion object : IntEntityClass<TableType>(TableTypes)

    val title by TableTypes.title

    override fun model() = TableTypeModel(id.value, title)
}