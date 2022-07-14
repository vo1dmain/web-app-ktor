package ru.vo1d.web.data.repos.impl

import ru.vo1d.web.data.dao.*
import ru.vo1d.web.data.filters.daybook.SessionFilters
import ru.vo1d.web.data.filters.daybook.TimetableFilters
import ru.vo1d.web.data.repos.DaybookRepo
import ru.vo1d.web.entities.daybook.Meta
import ru.vo1d.web.entities.daybook.timetable.TimetableModel
import ru.vo1d.web.entities.daybook.timetable.session.SessionModel

abstract class DaybookRepoImpl : DaybookRepo {
    protected abstract val timetableDao: TimetableDao
    protected abstract val sessionDao: SessionDao
    protected abstract val dayDao: DayDao

    //Group
    protected abstract val gradLevelDao: GradLevelDao
    protected abstract val gradDegreeDao: GradDegreeDao
    protected abstract val eduFormDao: EduFormDao
    protected abstract val groupDao: GroupDao

    //Meta
    protected abstract val sessionTypeDao: SessionTypeDao
    protected abstract val timePeriodDao: TimePeriodDao
    protected abstract val weekOptionDao: WeekOptionDao
    protected abstract val tableTypeDao: TableTypeDao

    override suspend fun meta() = Meta(
        gradLevelDao.all(),
        gradDegreeDao.all(),
        eduFormDao.all(),
        tableTypeDao.all(),
        groupDao.all(),
        timePeriodDao.all(),
        sessionTypeDao.all(),
        weekOptionDao.all()
    )

    override suspend fun timetables(page: Int?, filtersProducer: TimetableFilters.() -> Unit): List<TimetableModel> {
        val offset = offset(page)
        val filters = TimetableFilters(filtersProducer)
        if (filters.areEmpty()) return timetableDao.list(offset, limit)
        return timetableDao.filter(filters, offset, limit)
    }

    override suspend fun timetable(id: Int) = timetableDao.read(id)

    override suspend fun addTimetable(item: TimetableModel) = timetableDao.create(item)

    override suspend fun sessions(page: Int?, filtersProducer: SessionFilters.() -> Unit): List<SessionModel> {
        val offset = offset(page)
        val filters = SessionFilters(filtersProducer)
        if (filters.areEmpty()) return sessionDao.list(offset, limit)
        return sessionDao.filter(filters, offset, limit)
    }

    override suspend fun addSession(item: SessionModel) = sessionDao.create(item)
}