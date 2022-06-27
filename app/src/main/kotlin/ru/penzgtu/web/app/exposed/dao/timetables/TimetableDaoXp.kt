package ru.penzgtu.web.app.exposed.dao.timetables

import ru.penzgtu.web.app.data.dao.TimetableDao
import ru.penzgtu.web.app.data.filters.daybook.TimetableFilters
import ru.penzgtu.web.entities.daybook.timetable.TimetableModel
import ru.penzgtu.web.entities.daybook.timetable.TimetableView

class TimetableDaoXp : TimetableDao {
    override suspend fun create(item: TimetableModel): Int {
        TODO("Not yet implemented")
    }

    override suspend fun read(id: Int): TimetableModel? {
        TODO("Not yet implemented")
    }

    override suspend fun update(item: TimetableModel): Int {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: Int): Int {
        TODO("Not yet implemented")
    }

    override suspend fun list(offset: Long, limit: Int): List<TimetableView> {
        TODO("Not yet implemented")
    }

    override suspend fun filter(filters: TimetableFilters, offset: Long, limit: Int): List<TimetableView> {
        TODO("Not yet implemented")
    }
}