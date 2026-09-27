package ru.vo1d.web.exposed.dao.daybook.group

import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.core.inList
import org.jetbrains.exposed.v1.jdbc.batchInsert
import org.jetbrains.exposed.v1.jdbc.deleteWhere
import org.jetbrains.exposed.v1.jdbc.insertIgnoreAndGetId
import org.jetbrains.exposed.v1.jdbc.update
import ru.vo1d.web.data.dao.TableTypeDao
import ru.vo1d.web.entities.daybook.group.TableType
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