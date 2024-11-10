package ru.vo1d.web.exposed.dao.daybook.group

import org.jetbrains.exposed.sql.SqlExpressionBuilder.inList
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertIgnoreAndGetId
import org.jetbrains.exposed.sql.update
import ru.vo1d.web.data.dao.GradLevelDao
import ru.vo1d.web.entities.daybook.group.level.GraduationLevel
import ru.vo1d.web.exposed.entities.daybook.group.GraduationLevelEntity
import ru.vo1d.web.exposed.entities.daybook.group.GraduationLevels
import ru.vo1d.web.exposed.mappers.mapItem
import ru.vo1d.web.exposed.mappers.toDomain

class GradLevelDaoXp : GradLevelDao {
    override suspend fun create(item: GraduationLevel): String? {
        return GraduationLevels.insertIgnoreAndGetId { it.mapItem(item) }?.value
    }

    override suspend fun create(vararg items: GraduationLevel): Int {
        return GraduationLevels.batchInsert(items.asIterable(), ignore = true) { mapItem(it) }.count()
    }

    override suspend fun read(id: String): GraduationLevel? {
        return GraduationLevelEntity.findById(id)?.toDomain()
    }

    override suspend fun update(item: GraduationLevel): Int {
        return GraduationLevels.update({ GraduationLevels.id eq item.id }) { it[title] = item.title }
    }

    override suspend fun delete(vararg items: GraduationLevel): Int {
        return GraduationLevels.deleteWhere { GraduationLevels.id inList items.map { it.id } }
    }

    override suspend fun all(): List<GraduationLevel> {
        return GraduationLevelEntity.all().map(GraduationLevelEntity::toDomain)
    }
}