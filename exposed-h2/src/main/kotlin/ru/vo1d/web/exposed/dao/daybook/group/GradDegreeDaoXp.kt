package ru.vo1d.web.exposed.dao.daybook.group

import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertIgnoreAndGetId
import org.jetbrains.exposed.sql.update
import ru.vo1d.web.data.dao.GradDegreeDao
import ru.vo1d.web.entities.daybook.group.degree.GraduationDegree
import ru.vo1d.web.exposed.entities.daybook.group.GraduationDegreeEntity
import ru.vo1d.web.exposed.entities.daybook.group.GraduationDegrees
import ru.vo1d.web.exposed.mappers.mapItem
import ru.vo1d.web.exposed.mappers.toDomain

class GradDegreeDaoXp : GradDegreeDao {
    override suspend fun create(item: GraduationDegree): String? {
        return GraduationDegrees.insertIgnoreAndGetId { it.mapItem(item) }?.value
    }

    override suspend fun create(vararg items: GraduationDegree): Int {
        return GraduationDegrees.batchInsert(items.asIterable(), ignore = true) { mapItem(it) }.count()
    }

    override suspend fun read(id: String): GraduationDegree? {
        return GraduationDegreeEntity.findById(id)?.toDomain()
    }

    override suspend fun update(item: GraduationDegree): Int {
        return GraduationDegrees.update({ GraduationDegrees.id eq item.id }) { it.mapItem(item) }
    }

    override suspend fun delete(vararg items: GraduationDegree): Int {
        return GraduationDegrees.deleteWhere { GraduationDegrees.id eq id }
    }

    override suspend fun all(): List<GraduationDegree> {
        return GraduationDegreeEntity.all().map(GraduationDegreeEntity::toDomain)
    }
}