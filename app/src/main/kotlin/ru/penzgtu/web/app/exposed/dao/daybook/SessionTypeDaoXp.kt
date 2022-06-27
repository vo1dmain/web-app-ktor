package ru.penzgtu.web.app.exposed.dao.daybook

import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import ru.penzgtu.web.app.data.dao.SessionTypeDao
import ru.penzgtu.web.app.exposed.orm.daybook.timetable.SessionType
import ru.penzgtu.web.app.exposed.orm.daybook.timetable.SessionTypes
import ru.penzgtu.web.entities.daybook.timetable.session.SessionTypeModel

class SessionTypeDaoXp : SessionTypeDao {
    override suspend fun create(item: SessionTypeModel): Int {
        return SessionTypes.insertAndGetId {
            it[title] = item.title
        }.value
    }

    override suspend fun read(id: Int): SessionTypeModel? {
        return SessionType.findById(id)?.toModel()
    }

    override suspend fun update(item: SessionTypeModel): Int {
        return SessionTypes.insertAndGetId {
            it[title] = item.title
        }.value
    }

    override suspend fun delete(id: Int): Int {
        return SessionTypes.deleteWhere { SessionTypes.id eq id }
    }

    override suspend fun all(): List<SessionTypeModel> {
        return SessionType.all().map(SessionType::toModel)
    }
}