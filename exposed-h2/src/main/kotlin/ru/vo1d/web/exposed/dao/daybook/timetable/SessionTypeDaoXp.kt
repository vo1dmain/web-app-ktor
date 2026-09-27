package ru.vo1d.web.exposed.dao.daybook.timetable

import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.core.inList
import org.jetbrains.exposed.v1.jdbc.batchInsert
import org.jetbrains.exposed.v1.jdbc.deleteWhere
import org.jetbrains.exposed.v1.jdbc.insertIgnoreAndGetId
import org.jetbrains.exposed.v1.jdbc.update
import ru.vo1d.web.data.dao.SessionTypeDao
import ru.vo1d.web.entities.daybook.timetable.session.SessionType
import ru.vo1d.web.exposed.context.DbContext
import ru.vo1d.web.exposed.dao.XpDao
import ru.vo1d.web.exposed.entities.daybook.timetable.SessionTypeEntity
import ru.vo1d.web.exposed.entities.daybook.timetable.SessionTypes
import ru.vo1d.web.exposed.mappers.mapItem
import ru.vo1d.web.exposed.mappers.toDomain

class SessionTypeDaoXp(ctx: DbContext) : XpDao(ctx.daybook), SessionTypeDao {
    override suspend fun create(item: SessionType): Int? = query {
        SessionTypes.insertIgnoreAndGetId { it.mapItem(item) }?.value
    }

    override suspend fun create(vararg items: SessionType): Int = query {
        SessionTypes.batchInsert(items.asIterable(), ignore = true) { mapItem(it) }.count()
    }

    override suspend fun read(id: Int): SessionType? = query {
        SessionTypeEntity.findById(id)?.toDomain()
    }

    override suspend fun update(item: SessionType): Int = query {
        SessionTypes.update({ SessionTypes.id eq item.id }) { it.mapItem(item) }
    }

    override suspend fun delete(vararg items: SessionType): Int = query {
        SessionTypes.deleteWhere { SessionTypes.id inList items.mapNotNull { it.id } }
    }

    override suspend fun all(): List<SessionType> = query {
        SessionTypeEntity.all()
            .sortedBy { it.id.value }
            .map(SessionTypeEntity::toDomain)
    }
}
