package ru.vo1d.web.app.exposed.dao.daybook.timetable

import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import ru.vo1d.web.app.data.dao.SessionTypeDao
import ru.vo1d.web.app.exposed.orm.daybook.timetable.SessionType
import ru.vo1d.web.app.exposed.orm.daybook.timetable.SessionTypes
import ru.vo1d.web.entities.daybook.timetable.session.SessionTypeModel

class SessionTypeDaoXp : SessionTypeDao {
    override suspend fun create(item: SessionTypeModel) = SessionTypes.insertAndGetId {
        it[title] = item.title
    }.value

    override suspend fun read(id: Int) = SessionType.findById(id)?.toModel()

    override suspend fun update(item: SessionTypeModel) = SessionTypes.insertAndGetId {
        it[title] = item.title
    }.value

    override suspend fun delete(id: Int) = SessionTypes.deleteWhere { SessionTypes.id eq id }

    override suspend fun all() = SessionType.all().map(SessionType::toModel)
}