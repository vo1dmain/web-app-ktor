package ru.penzgtu.web.app.data.repos

import ru.penzgtu.web.app.data.dao.*
import ru.penzgtu.web.app.data.filters.timetables.TimetableFilters
import ru.penzgtu.web.entities.timetables.list.TimetableModel
import ru.penzgtu.web.entities.timetables.list.TimetableView
import ru.penzgtu.web.entities.timetables.meta.Meta
import ru.penzgtu.web.entities.timetables.meta.Week

abstract class TimetablesRepo : ListRepo {
    protected abstract val timetableDao: TimetableDao

    //Meta
    protected abstract val graduationLevelDao: GraduationLevelDao
    protected abstract val educationFormDao: EducationFormDao
    protected abstract val groupDao: GroupDao
    protected abstract val sessionTypeDao: SessionTypeDao
    protected abstract val tableTypeDao: TableTypeDao
    protected abstract val timePeriodDao: TimePeriodDao
    protected abstract val weekOptionDao: WeekOptionDao


    suspend fun meta(): Meta {
        return Meta(
            graduationLevelDao.all(),
            educationFormDao.all(),
            tableTypeDao.all(),
            groupDao.all(),
            timePeriodDao.all(),
            sessionTypeDao.all(),
            weekOptionDao.all()
        )
    }

    suspend fun list(filters: TimetableFilters?, page: Int?): List<TimetableView> {
        val offset = offset(page)

        if (filters == null) return timetableDao.list(offset, limit)
        return timetableDao.filter(filters, offset, limit)
    }

    suspend fun item(id: Int): TimetableModel? {
        return timetableDao.read(id)
    }

    suspend fun week(): Week? {
        TODO()
    }
}