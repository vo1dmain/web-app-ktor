package ru.vo1d.web.orm.dao.daybook.timetable

import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertIgnoreAndGetId
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.update
import ru.vo1d.web.data.dao.SessionTypeDao
import ru.vo1d.web.entities.daybook.timetable.session.SessionTypeModel
import ru.vo1d.web.orm.dao.XpDao
import ru.vo1d.web.orm.entities.daybook.timetable.SessionType
import ru.vo1d.web.orm.entities.daybook.timetable.SessionTypes

class SessionTypeDaoXp : SessionTypeDao, XpDao<SessionTypeModel> {
    override suspend fun create(item: SessionTypeModel) =
        SessionTypes.insertIgnoreAndGetId { it.mapItem(item) }?.value

    override suspend fun create(vararg items: SessionTypeModel) =
        SessionTypes.batchInsert(items.asIterable(), ignore = true) { mapItem(it) }.count()

    override suspend fun read(id: Int) = SessionType.findById(id)?.toModel()

    override suspend fun update(item: SessionTypeModel) =
        SessionTypes.update({ SessionTypes.id eq item.id }) { it.mapItem(item) }

    override suspend fun delete(id: Int) =
        SessionTypes.deleteWhere { SessionTypes.id eq id }

    override suspend fun all() = SessionType.all().sortedBy { it.id }.map(SessionType::toModel)
    override fun UpdateBuilder<*>.mapItem(item: SessionTypeModel) {
        this[SessionTypes.title] = item.title
    }
}