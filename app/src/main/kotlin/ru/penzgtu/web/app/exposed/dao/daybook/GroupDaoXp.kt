package ru.penzgtu.web.app.exposed.dao.daybook

import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.update
import ru.penzgtu.web.app.data.dao.GroupDao
import ru.penzgtu.web.app.exposed.orm.daybook.group.Group
import ru.penzgtu.web.app.exposed.orm.daybook.group.Groups
import ru.penzgtu.web.entities.daybook.group.GroupModel

class GroupDaoXp : GroupDao {
    override suspend fun create(item: GroupModel): String {
        return Groups.insertAndGetId {
            it[id] = item.code
            it[formId] = item.formId
            it[levelId] = item.levelId
        }.value
    }

    override suspend fun read(id: String): GroupModel? {
        return Group.findById(id)?.toModel()
    }

    override suspend fun update(item: GroupModel): Int {
        return Groups.update({ Groups.id eq item.code }) {
            it[formId] = item.formId
            it[levelId] = item.levelId
        }
    }

    override suspend fun delete(id: String): Int {
        return Groups.deleteWhere { Groups.id eq id }
    }

    override suspend fun all(): List<GroupModel> {
        return Group.all().map(Group::toModel)
    }
}