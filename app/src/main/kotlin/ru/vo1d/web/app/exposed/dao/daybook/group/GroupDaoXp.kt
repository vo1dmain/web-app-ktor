package ru.vo1d.web.app.exposed.dao.daybook.group

import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.update
import ru.vo1d.web.app.data.dao.GroupDao
import ru.vo1d.web.app.exposed.orm.daybook.group.Groups
import ru.vo1d.web.app.exposed.orm.daybook.group.Group
import ru.vo1d.web.entities.daybook.group.GroupModel

class GroupDaoXp : GroupDao {
    override suspend fun create(item: GroupModel) = Groups.insertAndGetId {
        it[id] = item.code
//        it[levelId] = item.levelId
//        it[degreeId] = item.degreeId
//        it[formId] = item.formId
    }.value

    override suspend fun read(id: String) = Group.findById(id)?.toModel()

    override suspend fun update(item: GroupModel) = Groups.update({ Groups.id eq item.code }) {
//        it[levelId] = item.levelId
//        it[degreeId] = item.degreeId
//        it[formId] = item.formId
    }

    override suspend fun delete(id: String) = Groups.deleteWhere { Groups.id eq id }

    override suspend fun all() = Group.all().map(Group::toModel)
}