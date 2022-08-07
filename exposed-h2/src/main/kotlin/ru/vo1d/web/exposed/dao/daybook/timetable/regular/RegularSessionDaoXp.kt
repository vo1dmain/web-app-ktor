package ru.vo1d.web.exposed.dao.daybook.timetable.regular

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import ru.vo1d.web.data.dao.RegularSessionDao
import ru.vo1d.web.data.filters.daybook.RegularSessionFilters
import ru.vo1d.web.entities.daybook.timetable.session.RegularSession
import ru.vo1d.web.entities.extensions.toDuration
import ru.vo1d.web.exposed.dao.XpDao
import ru.vo1d.web.exposed.entities.daybook.timetable.RegularSessionEntity
import ru.vo1d.web.exposed.entities.daybook.timetable.RegularSessions
import ru.vo1d.web.exposed.entities.daybook.timetable.TimetableRegularSessions

class RegularSessionDaoXp : RegularSessionDao, XpDao<RegularSession> {
    override suspend fun create(item: RegularSession) =
        RegularSessions.insertIgnoreAndGetId { it.mapItem(item) }?.value

    override suspend fun create(vararg items: RegularSession) =
        RegularSessions.batchInsert(items.asIterable(), ignore = true) { mapItem(it) }.count()

    override suspend fun read(id: Int) =
        RegularSessionEntity.findById(id)?.toModel()

    override suspend fun update(item: RegularSession) =
        RegularSessions.update({ RegularSessions.id eq item.id }) { it.mapItem(item) }

    override suspend fun delete(id: Int) =
        RegularSessions.deleteWhere { RegularSessions.id eq id }

    override suspend fun list(offset: Long, limit: Int) =
        RegularSessionEntity
            .all()
            .limit(limit, offset)
            .sortedWith(compareBy({ it.dayOfWeek }, { it.timeId }))
            .map(RegularSessionEntity::toModel)

    override suspend fun filter(filters: RegularSessionFilters, offset: Long, limit: Int): List<RegularSession> {
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

        return RegularSessionEntity.wrapRows(query).limit(limit, offset).map(RegularSessionEntity::toModel)
    }

    override fun UpdateBuilder<*>.mapItem(item: RegularSession) {
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