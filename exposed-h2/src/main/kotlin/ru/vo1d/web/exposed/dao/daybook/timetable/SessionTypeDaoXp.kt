package ru.vo1d.web.exposed.dao.daybook.timetable

import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertIgnoreAndGetId
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.update
import ru.vo1d.web.data.dao.SessionTypeDao
import ru.vo1d.web.entities.daybook.timetable.session.SessionType
import ru.vo1d.web.exposed.dao.XpDao
import ru.vo1d.web.exposed.entities.daybook.timetable.SessionTypeEntity
import ru.vo1d.web.exposed.entities.daybook.timetable.SessionTypes

class SessionTypeDaoXp : SessionTypeDao, XpDao<SessionType> {
    override suspend fun create(item: SessionType) =
        SessionTypes.insertIgnoreAndGetId { it.mapItem(item) }?.value

    override suspend fun create(vararg items: SessionType) =
        SessionTypes.batchInsert(items.asIterable(), ignore = true) { mapItem(it) }.count()

    override suspend fun read(id: Int) =
        SessionTypeEntity.findById(id)?.toModel()

    override suspend fun update(item: SessionType) =
        SessionTypes.update({ SessionTypes.id eq item.id }) { it.mapItem(item) }

    override suspend fun delete(id: Int) =
        SessionTypes.deleteWhere { SessionTypes.id eq id }

    override suspend fun all() =
        SessionTypeEntity.all().sortedBy { it.id }.map(SessionTypeEntity::toModel)

    override fun UpdateBuilder<*>.mapItem(item: SessionType) {
        this[SessionTypes.title] = item.title
    }
}