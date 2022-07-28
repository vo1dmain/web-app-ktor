package ru.vo1d.web.orm.dao.daybook.timetable.regular

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import ru.vo1d.web.data.dao.RegularSessionDao
import ru.vo1d.web.data.filters.daybook.RegularSessionFilters
import ru.vo1d.web.entities.daybook.timetable.session.RegularSessionModel
import ru.vo1d.web.entities.extensions.toDuration
import ru.vo1d.web.orm.dao.XpDao
import ru.vo1d.web.orm.entities.daybook.timetable.RegularSession
import ru.vo1d.web.orm.entities.daybook.timetable.RegularSessions
import ru.vo1d.web.orm.entities.daybook.timetable.TimetableRegularSessions

class RegularSessionDaoXp : RegularSessionDao, XpDao<RegularSessionModel> {
    override suspend fun create(item: RegularSessionModel) =
        RegularSessions.insertIgnoreAndGetId { it.mapItem(item) }?.value

    override suspend fun create(vararg items: RegularSessionModel) =
        RegularSessions.batchInsert(items.asIterable(), ignore = true) { mapItem(it) }.count()

    override suspend fun read(id: Int) = RegularSession.findById(id)?.toModel()

    override suspend fun update(item: RegularSessionModel) =
        RegularSessions.update({ RegularSessions.id eq item.id }) { it.mapItem(item) }

    override suspend fun delete(id: Int) = RegularSessions.deleteWhere { RegularSessions.id eq id }

    override suspend fun list(offset: Long, limit: Int) =
        RegularSession.all().limit(limit, offset).map(RegularSession::toModel)

    override suspend fun filter(filters: RegularSessionFilters, offset: Long, limit: Int): List<RegularSessionModel> {
        if (filters.areEmpty()) return list(offset, limit)

        val query = RegularSessions.selectAll().apply {
            filters.timetableId?.let {
                adjustColumnSet { innerJoin(TimetableRegularSessions) }
                andWhere { TimetableRegularSessions.timetableId eq it }
            }
            filters.subject?.let {
                andWhere { RegularSessions.subject eq it }
            }
            filters.instructor?.let {
                andWhere { RegularSessions.instructor eq it }
            }
            filters.place?.let {
                andWhere { RegularSessions.place eq it }
            }
            filters.dayOfWeek?.let {
                andWhere { RegularSessions.dayOfWeek eq it }
            }
            filters.timeId?.let {
                andWhere { RegularSessions.timeId eq it }
            }
            filters.typeId?.let {
                andWhere { RegularSessions.typeId eq it }
            }
            filters.weekOptionId?.let {
                andWhere { RegularSessions.weekOption eq it }
            }
        }

        return RegularSession.wrapRows(query).limit(limit, offset).map(RegularSession::toModel)
    }

    override fun UpdateBuilder<*>.mapItem(item: RegularSessionModel) {
        this[RegularSessions.subject] = item.subject
        this[RegularSessions.instructor] = item.instructor
        this[RegularSessions.place] = item.place
        this[RegularSessions.typeId] = item.typeId
        item.duration?.let { this[RegularSessions.duration] = it.toDuration() }
        this[RegularSessions.dayOfWeek] = item.dayOfWeek
        this[RegularSessions.timeId] = item.timeId
        this[RegularSessions.weekOption] = item.weekOption
    }
}