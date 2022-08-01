package ru.vo1d.web.orm.dao.daybook.group

import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertIgnoreAndGetId
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.update
import ru.vo1d.web.data.dao.TableTypeDao
import ru.vo1d.web.entities.daybook.group.type.TableType
import ru.vo1d.web.orm.dao.XpDao
import ru.vo1d.web.orm.entities.daybook.group.TableTypeEntity
import ru.vo1d.web.orm.entities.daybook.group.TableTypes

class TableTypeDaoXp : TableTypeDao, XpDao<TableType> {
    override suspend fun create(item: TableType) =
        TableTypes.insertIgnoreAndGetId { it.mapItem(item) }?.value

    override suspend fun create(vararg items: TableType) =
        TableTypes.batchInsert(items.asIterable(), ignore = true) { mapItem(it) }.count()

    override suspend fun read(id: String) =
        TableTypeEntity.findById(id)?.toModel()

    override suspend fun update(item: TableType) =
        TableTypes.update({ TableTypes.id eq item.id }) { it[title] = item.title }

    override suspend fun delete(id: String) =
        TableTypes.deleteWhere { TableTypes.id eq id }

    override suspend fun all() =
        TableTypeEntity.all().map(TableTypeEntity::toModel)

    override fun UpdateBuilder<*>.mapItem(item: TableType) {
        this[TableTypes.id] = item.id
        this[TableTypes.title] = item.title
    }
}