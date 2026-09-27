package ru.vo1d.web.persistence.dao.daybook.group

import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.core.inList
import org.jetbrains.exposed.v1.jdbc.batchInsert
import org.jetbrains.exposed.v1.jdbc.deleteWhere
import org.jetbrains.exposed.v1.jdbc.insertIgnoreAndGetId
import org.jetbrains.exposed.v1.jdbc.update
import ru.vo1d.web.domain.dao.GradLevelDao
import ru.vo1d.web.domain.daybook.group.GraduationLevel
import ru.vo1d.web.persistence.context.DbContext
import ru.vo1d.web.persistence.dao.XpDao
import ru.vo1d.web.persistence.entities.daybook.group.GraduationLevelEntity
import ru.vo1d.web.persistence.entities.daybook.group.GraduationLevels
import ru.vo1d.web.persistence.mappers.mapItem
import ru.vo1d.web.persistence.mappers.toDomain

class GradLevelDaoXp(ctx: DbContext) : XpDao(ctx.daybook), GradLevelDao {
    override suspend fun create(item: GraduationLevel): String? = query {
        GraduationLevels.insertIgnoreAndGetId { it.mapItem(item) }?.value
    }

    override suspend fun create(vararg items: GraduationLevel): Int = query {
        GraduationLevels.batchInsert(items.asIterable(), ignore = true) { mapItem(it) }.count()
    }

    override suspend fun read(id: String): GraduationLevel? = query {
        GraduationLevelEntity.findById(id)?.toDomain()
    }

    override suspend fun update(item: GraduationLevel): Int = query {
        GraduationLevels.update({ GraduationLevels.id eq item.id }) { it[title] = item.title }
    }

    override suspend fun delete(vararg items: GraduationLevel): Int = query {
        GraduationLevels.deleteWhere { GraduationLevels.id inList items.map { it.id } }
    }

    override suspend fun all(): List<GraduationLevel> = query {
        GraduationLevelEntity.all().map(GraduationLevelEntity::toDomain)
    }
}
