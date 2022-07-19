package ru.vo1d.web.data.repos.impl

import ru.vo1d.web.data.dao.*
import ru.vo1d.web.data.filters.daybook.SessionFilters
import ru.vo1d.web.data.filters.daybook.TimetableFilters
import ru.vo1d.web.data.filters.filters
import ru.vo1d.web.data.repos.DaybookRepo
import ru.vo1d.web.entities.daybook.Meta
import ru.vo1d.web.entities.daybook.timetable.TimetableModel
import ru.vo1d.web.entities.daybook.timetable.session.SessionModel
import ru.vo1d.web.entities.daybook.timetable.session.TimetableSessionModel

abstract class DaybookRepoImpl : DaybookRepo {
    protected abstract val timetableDao: TimetableDao
    protected abstract val sessionDao: SessionDao
    protected abstract val timetableSessionDao: TimetableSessionDao
    protected abstract val dayDao: DayDao

    //Group
    protected abstract val gradLevelDao: GradLevelDao
    protected abstract val gradDegreeDao: GradDegreeDao
    protected abstract val eduFormDao: EduFormDao
    protected abstract val groupDao: GroupDao

    //Meta
    protected abstract val sessionTypeDao: SessionTypeDao
    protected abstract val startTimeDao: StartTimeDao
    protected abstract val weekOptionDao: WeekOptionDao
    protected abstract val tableTypeDao: TableTypeDao

    override suspend fun meta() = Meta(
        levels(),
        degrees(),
        forms(),
        tableTypes(),
        groups(),
        startTimes(),
        sessionTypes(),
        weekOptions()
    )

    override suspend fun levels() = gradLevelDao.all()

    override suspend fun degrees() = gradDegreeDao.all()

    override suspend fun forms() = eduFormDao.all()

    override suspend fun tableTypes() = tableTypeDao.all()

    override suspend fun groups() = groupDao.all()

    override suspend fun startTimes() = startTimeDao.all()

    override suspend fun sessionTypes() = sessionTypeDao.all()

    override suspend fun weekOptions() = weekOptionDao.all()


    override suspend fun timetables(
        page: Int?,
        filtersBuilder: TimetableFilters.Builder.() -> Unit
    ): List<TimetableModel> {
        val offset = offset(page)
        val filters = filters(filtersBuilder)
        if (filters.areEmpty()) return timetableDao.list(offset, limit)
        return timetableDao.filter(filters, offset, limit)
    }

    override suspend fun timetable(id: Int) = timetableDao.read(id)

    override suspend fun addTimetable(item: TimetableModel) = timetableDao.create(item)


    override suspend fun sessions(
        page: Int?,
        filtersBuilder: SessionFilters.Builder.() -> Unit
    ): List<SessionModel> {
        val offset = offset(page)
        val filters = filters(filtersBuilder)
        if (filters.areEmpty()) return sessionDao.list(offset, limit)
        return sessionDao.filter(filters, offset, limit)
    }

    override suspend fun addSession(item: SessionModel) = sessionDao.create(item)

    override suspend fun addJunction(item: TimetableSessionModel) = timetableSessionDao.create(item)
}