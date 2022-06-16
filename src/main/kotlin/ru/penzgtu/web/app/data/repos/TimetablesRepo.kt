package ru.penzgtu.web.app.data.repos

import ru.penzgtu.web.app.data.dao.*
import ru.penzgtu.web.app.data.entities.timetables.main.Timetable
import ru.penzgtu.web.app.data.entities.timetables.main.TimetableView
import ru.penzgtu.web.app.data.entities.timetables.meta.Meta
import ru.penzgtu.web.app.data.entities.timetables.meta.Week
import ru.penzgtu.web.app.data.util.FilterParams

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