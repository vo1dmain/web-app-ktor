package ru.vo1d.web.orm.dao.daybook.timetable.dated

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import ru.vo1d.web.data.dao.DatedSessionDao
import ru.vo1d.web.data.filters.daybook.DatedSessionFilters
import ru.vo1d.web.entities.daybook.timetable.session.DatedSession
import ru.vo1d.web.entities.extensions.toDuration
import ru.vo1d.web.orm.dao.XpDao
import ru.vo1d.web.orm.entities.daybook.timetable.DatedSessionEntity
import ru.vo1d.web.orm.entities.daybook.timetable.DatedSessions
import ru.vo1d.web.orm.entities.daybook.timetable.TimetableDatedSessions

class DatedSessionDaoXp : DatedSessionDao, XpDao<DatedSession> {
    override suspend fun create(item: DatedSession) =
        DatedSessions.insertIgnoreAndGetId { it.mapItem(item) }?.value

    override suspend fun create(vararg items: DatedSession) =
        DatedSessions.batchInsert(items.asIterable(), ignore = true) { mapItem(it) }.count()

    override suspend fun read(id: Int) =
        DatedSessionEntity.findById(id)?.toModel()

    override suspend fun update(item: DatedSession) =
        DatedSessions.update({ DatedSessions.id eq item.id }) { it.mapItem(item) }

    override suspend fun delete(id: Int) =
        DatedSessions.deleteWhere { DatedSessions.id eq id }

    override suspend fun list(offset: Long, limit: Int) =
        DatedSessionEntity
            .all()
            .limit(limit, offset)
            .sortedBy { it.datetime }
            .map(DatedSessionEntity::toModel)

    override suspend fun filter(filters: DatedSessionFilters, offset: Long, limit: Int): List<DatedSession> {
        if (filters.areEmpty()) return list(offset, limit)

        val query = DatedSessions.selectAll().apply {
            filters.timetableId?.let {
                adjustColumnSet { innerJoin(TimetableDatedSessions) }
                andWhere { TimetableDatedSessions.timetableId eq it }
            }
            filters.subject?.let {
                andWhere { DatedSessions.subject eq it }
            }
            filters.instructor?.let {
                andWhere { DatedSessions.instructor eq it }
            }
            filters.place?.let {
                andWhere { DatedSessions.place eq it }
            }
            filters.typeId?.let {
                andWhere { DatedSessions.typeId eq it }
            }
            filters.datetime?.let {
                andWhere { DatedSessions.datetime eq it }
            }
        }

        return DatedSessionEntity.wrapRows(query).limit(limit, offset).map(DatedSessionEntity::toModel)
    }

    override fun UpdateBuilder<*>.mapItem(item: DatedSession) {
        this[DatedSessions.subject] = item.subject
        this[DatedSessions.instructor] = item.instructor
        this[DatedSessions.place] = item.place
        this[DatedSessions.typeId] = item.typeId
        item.duration?.let { this[DatedSessions.duration] = it.toDuration() }
        this[DatedSessions.datetime] = item.datetime
        item.timeZone?.let { this[DatedSessions.timeZone] = it.id }
    }
}