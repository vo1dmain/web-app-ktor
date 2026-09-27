package ru.vo1d.web.persistence.dao.daybook.group

import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.core.inList
import org.jetbrains.exposed.v1.jdbc.batchInsert
import org.jetbrains.exposed.v1.jdbc.deleteWhere
import org.jetbrains.exposed.v1.jdbc.insertIgnoreAndGetId
import org.jetbrains.exposed.v1.jdbc.update
import ru.vo1d.web.domain.dao.TableTypeDao
import ru.vo1d.web.domain.daybook.group.TableType
import ru.vo1d.web.persistence.context.DbContext
import ru.vo1d.web.persistence.dao.XpDao
import ru.vo1d.web.persistence.entities.daybook.group.TableTypeEntity
import ru.vo1d.web.persistence.entities.daybook.group.TableTypes
import ru.vo1d.web.persistence.mappers.mapItem
import ru.vo1d.web.persistence.mappers.toDomain

class TableTypeDaoXp(ctx: DbContext) : XpDao(ctx.daybook), TableTypeDao {
    override suspend fun create(item: TableType): String? = query {
        TableTypes.insertIgnoreAndGetId { it.mapItem(item) }?.value
    }

    override suspend fun create(vararg items: TableType): Int = query {
        TableTypes.batchInsert(items.asIterable(), ignore = true) { mapItem(it) }.count()
    }

    override suspend fun read(id: String): TableType? = query {
        TableTypeEntity.findById(id)?.toDomain()
    }

    override suspend fun update(item: TableType): Int = query {
        TableTypes.update({ TableTypes.id eq item.id }) { it[title] = item.title }
    }

    override suspend fun delete(vararg items: TableType): Int = query {
        TableTypes.deleteWhere { TableTypes.id inList items.map { it.id } }
    }

    override suspend fun all(): List<TableType> = query {
        TableTypeEntity.all().map(TableTypeEntity::toDomain)
    }
}
