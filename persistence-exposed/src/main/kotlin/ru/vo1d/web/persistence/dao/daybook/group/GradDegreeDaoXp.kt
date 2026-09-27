package ru.vo1d.web.persistence.dao.daybook.group

import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.core.inList
import org.jetbrains.exposed.v1.jdbc.batchInsert
import org.jetbrains.exposed.v1.jdbc.deleteWhere
import org.jetbrains.exposed.v1.jdbc.insertIgnoreAndGetId
import org.jetbrains.exposed.v1.jdbc.update
import ru.vo1d.web.domain.dao.GradDegreeDao
import ru.vo1d.web.domain.daybook.group.GraduationDegree
import ru.vo1d.web.persistence.context.DbContext
import ru.vo1d.web.persistence.dao.XpDao
import ru.vo1d.web.persistence.entities.daybook.group.GraduationDegreeEntity
import ru.vo1d.web.persistence.entities.daybook.group.GraduationDegrees
import ru.vo1d.web.persistence.mappers.mapItem
import ru.vo1d.web.persistence.mappers.toDomain

class GradDegreeDaoXp(ctx: DbContext) : XpDao(ctx.daybook), GradDegreeDao {
    override suspend fun create(item: GraduationDegree): String? = query {
        GraduationDegrees.insertIgnoreAndGetId { it.mapItem(item) }?.value
    }

    override suspend fun create(vararg items: GraduationDegree): Int = query {
        GraduationDegrees.batchInsert(items.asIterable(), ignore = true) { mapItem(it) }.count()
    }

    override suspend fun read(id: String): GraduationDegree? = query {
        GraduationDegreeEntity.findById(id)?.toDomain()
    }

    override suspend fun update(item: GraduationDegree): Int = query {
        GraduationDegrees.update({ GraduationDegrees.id eq item.id }) { it.mapItem(item) }
    }

    override suspend fun delete(vararg items: GraduationDegree): Int = query {
        GraduationDegrees.deleteWhere { GraduationDegrees.id inList items.map { it.id } }
    }

    override suspend fun all(): List<GraduationDegree> = query {
        GraduationDegreeEntity.all().map(GraduationDegreeEntity::toDomain)
    }
}
