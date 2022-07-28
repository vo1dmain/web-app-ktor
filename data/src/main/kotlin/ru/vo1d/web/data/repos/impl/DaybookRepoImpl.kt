package ru.vo1d.web.data.repos.impl

import ru.vo1d.web.data.dao.*
import ru.vo1d.web.data.filters.daybook.DatedSessionFilters
import ru.vo1d.web.data.filters.daybook.RegularSessionFilters
import ru.vo1d.web.data.filters.daybook.TimetableFilters
import ru.vo1d.web.data.filters.filters
import ru.vo1d.web.data.repos.DaybookRepo
import ru.vo1d.web.entities.daybook.Meta
import ru.vo1d.web.entities.daybook.group.GroupModel
import ru.vo1d.web.entities.daybook.timetable.TimetableModel
import ru.vo1d.web.entities.daybook.timetable.session.DatedSessionModel
import ru.vo1d.web.entities.daybook.timetable.session.RegularSessionModel
import ru.vo1d.web.entities.daybook.timetable.session.TimetableSessionModel

abstract class DaybookRepoImpl : DaybookRepo {
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

    override suspend fun addGroups(vararg groups: GroupModel) = groupDao.create(*groups)

    override suspend fun startTimes() = startTimeDao.all()

    override suspend fun sessionTypes() = sessionTypeDao.all()


    override suspend fun timetables(
        page: Int?,
        filtersBuilder: TimetableFilters.Builder.() -> Unit
    ): List<TimetableModel> {
        val offset = offset(page)
        val filters = filters(filtersBuilder)
        if (filters.areEmpty()) return timetableDao.list(offset, limit)
        return timetableDao.filter(filters, offset, limit)
    }

    override suspend fun timetable(id: Int) = timetableDao.readLinked(id)

    override suspend fun timetableBase(id: Int) = timetableDao.read(id)

    override suspend fun addTimetable(item: TimetableModel) = timetableDao.create(item)

    override suspend fun timetableExists(code: String, type: String) = timetableDao.filter(
        filters<TimetableFilters, TimetableFilters.Builder> {
            groupCode = code
            typeId = type
        },
        offset = 0,
        limit = 1
    ).isNotEmpty()

    override suspend fun regularSessions(
        page: Int?,
        filtersBuilder: RegularSessionFilters.Builder.() -> Unit
    ): List<RegularSessionModel> {
        val offset = offset(page)
        val filters = filters(filtersBuilder)
        if (filters.areEmpty()) return regularSessionDao.list(offset, limit)
        return regularSessionDao.filter(filters, offset, limit)
    }

    override suspend fun datedSessions(
        page: Int?,
        filtersBuilder: DatedSessionFilters.Builder.() -> Unit
    ): List<DatedSessionModel> {
        val offset = offset(page)
        val filters = filters(filtersBuilder)
        if (filters.areEmpty()) return datedSessionDao.list(offset, limit)
        return datedSessionDao.filter(filters, offset, limit)
    }

    override suspend fun addRegularSession(item: RegularSessionModel) = regularSessionDao.create(item)

    override suspend fun addDatedSession(item: DatedSessionModel) = datedSessionDao.create(item)

    override suspend fun addRegularJunction(item: TimetableSessionModel) = timetableRegularSessionDao.create(item)

    override suspend fun addDatedJunction(item: TimetableSessionModel) = timetableDatedSessionDao.create(item)
}