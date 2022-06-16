package ru.penzgtu.web.app.data.repos

import ru.penzgtu.web.app.data.dao.TimetableDao
import ru.penzgtu.web.app.data.entities.timetables.main.Timetable
import ru.penzgtu.web.app.data.entities.timetables.main.TimetableView
import ru.penzgtu.web.app.data.entities.timetables.meta.Meta
import ru.penzgtu.web.app.data.entities.timetables.meta.Week
import ru.penzgtu.web.app.data.util.FilterParams

abstract class TimetablesRepo: ListRepo {
    protected abstract val timetableDao: TimetableDao

    suspend fun meta(): Meta {
        TODO()
    }

    suspend fun list(params: FilterParams?, page: Int?): List<TimetableView> {
        val offset = offset(page)
        return params?.let { timetableDao.filter(params, offset, limit) } ?: timetableDao.list(offset, limit)
    }

    suspend fun item(id: Int): Timetable? {
        return timetableDao.read(id)
    }

    suspend fun week(): Week? {
        TODO()
    }
}