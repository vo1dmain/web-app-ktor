package ru.vo1d.web.exposed.dao.daybook.group

import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertIgnoreAndGetId
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.update
import ru.vo1d.web.data.dao.GroupDao
import ru.vo1d.web.entities.daybook.group.Group
import ru.vo1d.web.exposed.dao.XpDao
import ru.vo1d.web.exposed.entities.daybook.group.GroupWithTypesEntity
import ru.vo1d.web.exposed.entities.daybook.group.Groups

class GroupDaoXp : GroupDao, XpDao<Group> {
    override suspend fun create(item: Group) =
        Groups.insertIgnoreAndGetId { it.mapItem(item) }?.value

    override suspend fun create(vararg items: Group) =
        Groups.batchInsert(items.asIterable(), ignore = true) { mapItem(it) }.count()

    override suspend fun read(id: String) =
        GroupWithTypesEntity.findById(id)?.toModel()

    override suspend fun update(item: Group) =
        Groups.update({ Groups.id eq item.code }) {
            it[levelId] = item.levelId
            it[degreeId] = item.degreeId
            it[formId] = item.formId
        }

    override suspend fun delete(id: String) =
        Groups.deleteWhere { Groups.id eq id }

    override suspend fun all() =
        GroupWithTypesEntity.all().sortedBy { it.id }.map(GroupWithTypesEntity::toModel)

    override fun UpdateBuilder<*>.mapItem(item: Group) {
        this[Groups.id] = item.code
        this[Groups.levelId] = item.levelId
        this[Groups.degreeId] = item.degreeId
        this[Groups.formId] = item.formId
    }
}