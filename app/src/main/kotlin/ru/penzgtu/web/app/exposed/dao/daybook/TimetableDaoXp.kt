package ru.penzgtu.web.app.exposed.dao.daybook

import org.jetbrains.exposed.sql.*
import ru.penzgtu.web.app.data.dao.TimetableDao
import ru.penzgtu.web.app.data.filters.daybook.TimetableFilters
import ru.penzgtu.web.app.exposed.orm.daybook.timetable.*
import ru.penzgtu.web.entities.daybook.timetable.TimetableModel
import ru.penzgtu.web.entities.daybook.timetable.TimetableView

class TimetableDaoXp : TimetableDao {
    override suspend fun create(item: TimetableModel): Int {
        Days.batchInsert(item.days, ignore = true) {
            this[Days.id] = it.id!!
            this[Days.title] = it.title
        }

        val insertedSessions = Sessions.batchInsert(item.sessions, ignore = true) {
            this[Sessions.id] = it.id!!
            this[Sessions.subject] = it.subject
            this[Sessions.instructor] = it.instructor
            this[Sessions.place] = it.place
            this[Sessions.typeId] = it.typeId
            this[Sessions.dayId] = it.dayId
            this[Sessions.periodId] = it.periodId
            this[Sessions.weekOptionId] = it.weekOptionId
        }

        val id = Timetables.insertAndGetId {
            it[groupCode] = item.groupCode
            it[typeId] = item.typeId
        }.value

        TimetableSessions.batchInsert(insertedSessions, ignore = true, shouldReturnGeneratedValues = false) {
            this[TimetableSessions.sessionId] = it[Sessions.id]
            this[TimetableSessions.timetableId] = id
        }

        return id
    }

    override suspend fun read(id: Int): TimetableModel? {
        return Timetable.findById(id)?.toModel()
    }

    override suspend fun update(item: TimetableModel): Int {
        return Timetables.update({ Timetables.id eq item.id }) {
            it[groupCode] = item.groupCode
            it[typeId] = item.typeId
        }
    }

    override suspend fun delete(id: Int): Int {
        return Timetables.deleteWhere { Timetables.id eq id }
    }

    override suspend fun list(offset: Long, limit: Int): List<TimetableView> {
        return Timetable.all().limit(limit, offset).map(Timetable::toView)
    }

    override suspend fun filter(filters: TimetableFilters, offset: Long, limit: Int): List<TimetableView> {
        if (filters.areEmpty()) return list(offset, limit)

        return Timetable.find {
            (Timetables.typeId eq filters.typeId) or (Timetables.groupCode eq filters.groupCode)
        }.limit(limit, offset).map(Timetable::toView)
    }
}