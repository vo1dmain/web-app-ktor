package ru.vo1d.web.app.data.repos

import ru.vo1d.web.app.data.dao.*
import ru.vo1d.web.app.data.filters.daybook.TimetableFilters
import ru.vo1d.web.entities.daybook.Meta
import ru.vo1d.web.entities.daybook.timetable.TimetableModel
import ru.vo1d.web.entities.daybook.timetable.TimetableView

abstract class DaybookRepo : ListRepo {
    protected abstract val timetableDao: TimetableDao

    protected abstract val sessionTypeDao: SessionTypeDao
    protected abstract val timePeriodDao: TimePeriodDao
    protected abstract val weekOptionDao: WeekOptionDao

    //Meta
    protected abstract val gradLevelDao: GradLevelDao
    protected abstract val gradDegreeDao: GradDegreeDao
    protected abstract val eduFormDao: EduFormDao
    protected abstract val tableTypeDao: TableTypeDao
    protected abstract val groupDao: GroupDao


    open suspend fun meta(): Meta {
        return Meta(
            gradLevelDao.all(),
            gradDegreeDao.all(),
            eduFormDao.all(),
            tableTypeDao.all(),
            groupDao.all(),
            timePeriodDao.all(),
            sessionTypeDao.all(),
            weekOptionDao.all()
        )
    }

    open suspend fun timetables(filters: TimetableFilters?, page: Int?): List<TimetableView> {
        val offset = offset(page)

        if (filters == null) return timetableDao.list(offset, limit)
        return timetableDao.filter(filters, offset, limit)
    }

    open suspend fun timetable(id: Int): TimetableModel? {
        return timetableDao.read(id)
    }
}