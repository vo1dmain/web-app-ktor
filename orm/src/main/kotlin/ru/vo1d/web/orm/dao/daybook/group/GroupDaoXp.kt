package ru.vo1d.web.orm.dao.daybook.group

import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.update
import ru.vo1d.web.data.dao.GroupDao
import ru.vo1d.web.entities.daybook.group.GroupModel
import ru.vo1d.web.orm.dao.XpDao
import ru.vo1d.web.orm.entities.daybook.group.GroupWithTypes
import ru.vo1d.web.orm.entities.daybook.group.Groups

class GroupDaoXp : GroupDao, XpDao<GroupModel> {
    override suspend fun create(item: GroupModel) = Groups.insertAndGetId { it.mapItem(item) }.value

    override suspend fun create(vararg items: GroupModel) =
        Groups.batchInsert(items.asIterable()) { mapItem(it) }.count()

    override suspend fun read(id: String) = GroupWithTypes.findById(id)?.toDto()

    override suspend fun update(item: GroupModel) = Groups.update({ Groups.id eq item.code }) {
        it[levelId] = item.levelId
        it[degreeId] = item.degreeId
        it[formId] = item.formId
    }

    override suspend fun delete(id: String) = Groups.deleteWhere { Groups.id eq id }

    override suspend fun all() = GroupWithTypes.all().map(GroupWithTypes::toDto)

    override fun UpdateBuilder<Int>.mapItem(item: GroupModel) {
        this[Groups.id] = item.code
        this[Groups.levelId] = item.levelId
        this[Groups.degreeId] = item.degreeId
        this[Groups.formId] = item.formId
    }
}