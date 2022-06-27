package ru.vo1d.web.app.exposed.dao.daybook

import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.update
import ru.vo1d.web.app.data.dao.TableTypeDao
import ru.vo1d.web.app.exposed.orm.daybook.group.TableType
import ru.vo1d.web.app.exposed.orm.daybook.group.TableTypes
import ru.vo1d.web.entities.daybook.group.type.TableTypeModel

class TableTypeDaoXp : TableTypeDao {
    override suspend fun create(item: TableTypeModel): String {
        return TableTypes.insertAndGetId {
            it[id] = item.id
            it[title] = item.title
        }.value
    }

    override suspend fun read(id: String): TableTypeModel? {
        return TableType.findById(id)?.toModel()
    }

    override suspend fun update(item: TableTypeModel): Int {
        return TableTypes.update({ TableTypes.id eq item.id }) {
            it[title] = item.title
        }
    }

    override suspend fun delete(id: String): Int {
        return TableTypes.deleteWhere { TableTypes.id eq id }
    }

    override suspend fun all(): List<TableTypeModel> {
        return TableType.all().map(TableType::toModel)
    }
}