package ru.vo1d.web.orm.dao.daybook.timetable.dated

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import ru.vo1d.web.data.dao.DatedSessionDao
import ru.vo1d.web.data.filters.daybook.DatedSessionFilters
import ru.vo1d.web.entities.daybook.timetable.session.DatedSessionModel
import ru.vo1d.web.entities.extensions.toDuration
import ru.vo1d.web.orm.dao.XpDao
import ru.vo1d.web.orm.entities.daybook.timetable.DatedSession
import ru.vo1d.web.orm.entities.daybook.timetable.DatedSessions
import ru.vo1d.web.orm.entities.daybook.timetable.TimetableDatedSessions

class DatedSessionDaoXp : DatedSessionDao, XpDao<DatedSessionModel> {
    override suspend fun create(item: DatedSessionModel) =
        DatedSessions.insertIgnoreAndGetId { it.mapItem(item) }?.value

    override suspend fun create(vararg items: DatedSessionModel) =
        DatedSessions.batchInsert(items.asIterable(), ignore = true) { mapItem(it) }.count()

    override suspend fun read(id: Int) = DatedSession.findById(id)?.toModel()

    override suspend fun update(item: DatedSessionModel) =
        DatedSessions.update({ DatedSessions.id eq item.id }) { it.mapItem(item) }

    override suspend fun delete(id: Int) = DatedSessions.deleteWhere { DatedSessions.id eq id }

    override suspend fun list(offset: Long, limit: Int) =
        DatedSession.all().limit(limit, offset).map(DatedSession::toModel)

    override suspend fun filter(filters: DatedSessionFilters, offset: Long, limit: Int): List<DatedSessionModel> {
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

        return DatedSession.wrapRows(query).limit(limit, offset).map(DatedSession::toModel)
    }

    override fun UpdateBuilder<*>.mapItem(item: DatedSessionModel) {
        this[DatedSessions.subject] = item.subject
        this[DatedSessions.instructor] = item.instructor
        this[DatedSessions.place] = item.place
        this[DatedSessions.typeId] = item.typeId
        item.duration?.let { this[DatedSessions.duration] = it.toDuration() }
        this[DatedSessions.datetime] = item.datetime
        item.timeZone?.let { this[DatedSessions.timeZone] = it.id }
    }
}