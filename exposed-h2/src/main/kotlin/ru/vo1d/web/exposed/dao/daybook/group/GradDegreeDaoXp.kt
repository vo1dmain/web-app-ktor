package ru.vo1d.web.exposed.dao.daybook.group

import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertIgnoreAndGetId
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.update
import ru.vo1d.web.data.dao.GradDegreeDao
import ru.vo1d.web.entities.daybook.group.degree.GraduationDegree
import ru.vo1d.web.exposed.dao.XpDao
import ru.vo1d.web.exposed.entities.daybook.group.GraduationDegreeEntity
import ru.vo1d.web.exposed.entities.daybook.group.GraduationDegrees

class GradDegreeDaoXp : GradDegreeDao, XpDao<GraduationDegree> {
    override suspend fun create(item: GraduationDegree) =
        GraduationDegrees.insertIgnoreAndGetId { it.mapItem(item) }?.value

    override suspend fun create(vararg items: GraduationDegree) =
        GraduationDegrees.batchInsert(items.asIterable(), ignore = true) { mapItem(it) }.count()

    override suspend fun read(id: String) =
        GraduationDegreeEntity.findById(id)?.toModel()

    override suspend fun update(item: GraduationDegree) =
        GraduationDegrees.update({ GraduationDegrees.id eq item.id }) { it.mapItem(item) }

    override suspend fun delete(id: String) =
        GraduationDegrees.deleteWhere { GraduationDegrees.id eq id }

    override suspend fun all() =
        GraduationDegreeEntity.all().map(GraduationDegreeEntity::toModel)

    override fun UpdateBuilder<*>.mapItem(item: GraduationDegree) {
        this[GraduationDegrees.id] = item.id
        this[GraduationDegrees.title] = item.title
    }
}