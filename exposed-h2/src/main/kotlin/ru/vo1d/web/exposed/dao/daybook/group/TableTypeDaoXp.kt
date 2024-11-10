package ru.vo1d.web.exposed.dao.daybook.group

import org.jetbrains.exposed.sql.SqlExpressionBuilder.inList
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertIgnoreAndGetId
import org.jetbrains.exposed.sql.update
import ru.vo1d.web.data.dao.TableTypeDao
import ru.vo1d.web.entities.daybook.group.type.TableType
import ru.vo1d.web.exposed.entities.daybook.group.TableTypeEntity
import ru.vo1d.web.exposed.entities.daybook.group.TableTypes
import ru.vo1d.web.exposed.mappers.mapItem
import ru.vo1d.web.exposed.mappers.toDomain

class TableTypeDaoXp : TableTypeDao {
    override suspend fun create(item: TableType): String? {
        return TableTypes.insertIgnoreAndGetId { it.mapItem(item) }?.value
    }

    override suspend fun create(vararg items: TableType): Int {
        return TableTypes.batchInsert(items.asIterable(), ignore = true) { mapItem(it) }.count()
    }

    override suspend fun read(id: String): TableType? {
        return TableTypeEntity.findById(id)?.toDomain()
    }

    override suspend fun update(item: TableType): Int {
        return TableTypes.update({ TableTypes.id eq item.id }) { it[title] = item.title }
    }

    override suspend fun delete(vararg items: TableType): Int {
        return TableTypes.deleteWhere { TableTypes.id inList items.map { it.id } }
    }

    override suspend fun all(): List<TableType> {
        return TableTypeEntity.all().map(TableTypeEntity::toDomain)
    }
}