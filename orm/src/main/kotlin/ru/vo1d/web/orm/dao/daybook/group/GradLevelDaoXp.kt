package ru.vo1d.web.orm.dao.daybook.group

import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertIgnoreAndGetId
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.update
import ru.vo1d.web.data.dao.GradLevelDao
import ru.vo1d.web.entities.daybook.group.level.GradLevelModel
import ru.vo1d.web.orm.dao.XpDao
import ru.vo1d.web.orm.entities.daybook.group.GraduationLevel
import ru.vo1d.web.orm.entities.daybook.group.GraduationLevels

class GradLevelDaoXp : GradLevelDao, XpDao<GradLevelModel> {
    override suspend fun create(item: GradLevelModel) =
        GraduationLevels.insertIgnoreAndGetId { it.mapItem(item) }?.value

    override suspend fun create(vararg items: GradLevelModel) =
        GraduationLevels.batchInsert(items.asIterable(), ignore = true) { mapItem(it) }.count()

    override suspend fun read(id: String) = GraduationLevel.findById(id)?.toModel()

    override suspend fun update(item: GradLevelModel) =
        GraduationLevels.update({ GraduationLevels.id eq item.id }) { it[title] = item.title }

    override suspend fun delete(id: String) = GraduationLevels.deleteWhere { GraduationLevels.id eq id }

    override suspend fun all() = GraduationLevel.all().map(GraduationLevel::toModel)

    override fun UpdateBuilder<*>.mapItem(item: GradLevelModel) {
        this[GraduationLevels.id] = item.id
        this[GraduationLevels.title] = item.title
    }
}