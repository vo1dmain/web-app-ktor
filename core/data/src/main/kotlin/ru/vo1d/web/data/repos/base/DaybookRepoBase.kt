package ru.vo1d.web.data.repos.base

import ru.vo1d.web.data.dao.*
import ru.vo1d.web.data.filters.daybook.DatedSessionFilters
import ru.vo1d.web.data.filters.daybook.RegularSessionFilters
import ru.vo1d.web.data.filters.daybook.TimetableFilters
import ru.vo1d.web.data.repos.DaybookRepo
import ru.vo1d.web.entities.daybook.Meta
import ru.vo1d.web.entities.daybook.group.Group
import ru.vo1d.web.entities.daybook.timetable.Timetable
import ru.vo1d.web.entities.daybook.timetable.session.DatedSession
import ru.vo1d.web.entities.daybook.timetable.session.RegularSession
import ru.vo1d.web.entities.daybook.timetable.session.TimetableSession

abstract class DaybookRepoBase : DaybookRepo {
    protected abstract val timetableDao: TimetableDao

    protected abstract val regularSessionDao: RegularSessionDao
    protected abstract val datedSessionDao: DatedSessionDao

    protected abstract val timetableRegularSessionDao: TimetableRegularSessionDao
    protected abstract val timetableDatedSessionDao: TimetableDatedSessionDao

    //Group
    protected abstract val gradLevelDao: GradLevelDao
    protected abstract val gradDegreeDao: GradDegreeDao
    protected abstract val eduFormDao: EduFormDao
    protected abstract val groupDao: GroupDao

    //Meta
    protected abstract val sessionTypeDao: SessionTypeDao
    protected abstract val startTimeDao: StartTimeDao
    protected abstract val tableTypeDao: TableTypeDao

    override suspend fun meta() = Meta(
        weekNumber(),
        levels(),
        degrees(),
        forms(),
        tableTypes(),
        groups(),
        startTimes(),
        sessionTypes()
    )

    override suspend fun levels() = gradLevelDao.all()

    override suspend fun degrees() = gradDegreeDao.all()

    override suspend fun forms() = eduFormDao.all()

    override suspend fun tableTypes() = tableTypeDao.all()

    override suspend fun groups() = groupDao.all()

    override suspend fun addGroups(vararg groups: Group) = groupDao.create(*groups)

    override suspend fun startTimes() = startTimeDao.all()

    override suspend fun sessionTypes() = sessionTypeDao.all()


    override suspend fun timetables(page: Int?, filters: TimetableFilters): List<Timetable> {
        val offset = offset(page)

        return if (filters == TimetableFilters.Empty) timetableDao.list(offset, limit)
        else timetableDao.filter(filters, offset, limit)
    }

    override suspend fun timetable(id: Int) = timetableDao.readLinked(id)

    override suspend fun timetableBase(id: Int) = timetableDao.read(id)

    override suspend fun addTimetable(item: Timetable) = timetableDao.create(item)

    override suspend fun timetableExists(code: String, type: String) = timetableDao.filter(
        TimetableFilters(
            groupCode = code,
            typeId = type
        ),
        offset = 0,
        limit = 1
    ).isNotEmpty()

    override suspend fun regularSessions(page: Int?, filters: RegularSessionFilters): List<RegularSession> {
        val offset = offset(page)
        return if (filters == RegularSessionFilters.Empty) regularSessionDao.list(offset, limit)
        else regularSessionDao.filter(filters, offset, limit)
    }

    override suspend fun datedSessions(page: Int?, filters: DatedSessionFilters): List<DatedSession> {
        val offset = offset(page)
        return if (filters == DatedSessionFilters.Empty) datedSessionDao.list(offset, limit)
        else datedSessionDao.filter(filters, offset, limit)
    }

    override suspend fun addRegularSession(item: RegularSession) = regularSessionDao.create(item)

    override suspend fun addDatedSession(item: DatedSession) = datedSessionDao.create(item)

    override suspend fun addRegularJunction(item: TimetableSession) = timetableRegularSessionDao.create(item)

    override suspend fun addDatedJunction(item: TimetableSession) = timetableDatedSessionDao.create(item)
}