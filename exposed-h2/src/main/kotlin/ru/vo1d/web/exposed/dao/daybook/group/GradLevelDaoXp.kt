package ru.vo1d.web.exposed.dao.daybook.group

import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertIgnoreAndGetId
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.update
import ru.vo1d.web.data.dao.GradLevelDao
import ru.vo1d.web.entities.daybook.group.level.GraduationLevel
import ru.vo1d.web.exposed.dao.XpDao
import ru.vo1d.web.exposed.entities.daybook.group.GraduationLevelEntity
import ru.vo1d.web.exposed.entities.daybook.group.GraduationLevels

class GradLevelDaoXp : GradLevelDao, XpDao<GraduationLevel> {
    override suspend fun create(item: GraduationLevel) =
        GraduationLevels.insertIgnoreAndGetId { it.mapItem(item) }?.value

    override suspend fun create(vararg items: GraduationLevel) =
        GraduationLevels.batchInsert(items.asIterable(), ignore = true) { mapItem(it) }.count()

    override suspend fun read(id: String) =
        GraduationLevelEntity.findById(id)?.toModel()

    override suspend fun update(item: GraduationLevel) =
        GraduationLevels.update({ GraduationLevels.id eq item.id }) { it[title] = item.title }

    override suspend fun delete(id: String) =
        GraduationLevels.deleteWhere { GraduationLevels.id eq id }

    override suspend fun all() =
        GraduationLevelEntity.all().map(GraduationLevelEntity::toModel)

    override fun UpdateBuilder<*>.mapItem(item: GraduationLevel) {
        this[GraduationLevels.id] = item.id
        this[GraduationLevels.title] = item.title
    }
}