package ru.vo1d.web.exposed.dao.daybook.group

import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.core.inList
import org.jetbrains.exposed.v1.jdbc.batchInsert
import org.jetbrains.exposed.v1.jdbc.deleteWhere
import org.jetbrains.exposed.v1.jdbc.insertIgnoreAndGetId
import org.jetbrains.exposed.v1.jdbc.update
import ru.vo1d.web.data.dao.GroupDao
import ru.vo1d.web.entities.daybook.group.Group
import ru.vo1d.web.exposed.entities.daybook.group.GroupEntity
import ru.vo1d.web.exposed.entities.daybook.group.Groups
import ru.vo1d.web.exposed.mappers.mapItem
import ru.vo1d.web.exposed.mappers.toDomain

class GroupDaoXp : GroupDao {
    override suspend fun create(item: Group): String? {
        return Groups.insertIgnoreAndGetId { it.mapItem(item) }?.value
    }

    override suspend fun create(vararg items: Group): Int {
        return Groups.batchInsert(items.asIterable(), ignore = true) { mapItem(it) }.count()
    }

    override suspend fun read(id: String): Group? {
        return GroupEntity.findById(id)?.toDomain()
    }

    override suspend fun update(item: Group): Int {
        return Groups.update({ Groups.id eq item.code }) {
            it[levelId] = item.levelId
            it[degreeId] = item.degreeId
            it[formId] = item.formId
        }
    }

    override suspend fun delete(vararg items: Group): Int {
        return Groups.deleteWhere { Groups.id inList items.map { it.code } }
    }

    override suspend fun all(): List<Group> {
        return GroupEntity.all().map { it.toDomain() }
    }
}