package ru.vo1d.web.data.repos

import kotlinx.datetime.*
import org.kodein.di.DI
import org.kodein.di.instance
import ru.vo1d.web.data.dao.*
import ru.vo1d.web.data.filters.daybook.DatedSessionFilters
import ru.vo1d.web.data.filters.daybook.RegularSessionFilters
import ru.vo1d.web.data.filters.daybook.TimetableFilters
import ru.vo1d.web.entities.daybook.Meta
import ru.vo1d.web.entities.daybook.group.Group
import ru.vo1d.web.entities.daybook.group.degree.GraduationDegree
import ru.vo1d.web.entities.daybook.group.form.EducationForm
import ru.vo1d.web.entities.daybook.group.level.GraduationLevel
import ru.vo1d.web.entities.daybook.group.type.TableType
import ru.vo1d.web.entities.daybook.timetable.Timetable
import ru.vo1d.web.entities.daybook.timetable.session.DatedSession
import ru.vo1d.web.entities.daybook.timetable.session.RegularSession
import ru.vo1d.web.entities.daybook.timetable.session.SessionType
import ru.vo1d.web.entities.daybook.timetable.session.TimetableSession
import ru.vo1d.web.entities.daybook.timetable.time.StartTime
import ru.vo1d.web.entities.daybook.timetable.week.Week
import java.time.temporal.TemporalAdjusters

class DaybookRepo(di: DI) : ListRepo {
    private val timetableDao: TimetableDao by di.instance()
    private val regularSessionDao: RegularSessionDao by di.instance()
    private val datedSessionDao: DatedSessionDao by di.instance()
    private val timetableDatedSessionDao: TimetableDatedSessionDao by di.instance()
    private val timetableRegularSessionDao: TimetableRegularSessionDao by di.instance()
    private val gradLevelDao: GradLevelDao by di.instance()
    private val gradDegreeDao: GradDegreeDao by di.instance()
    private val eduFormDao: EduFormDao by di.instance()
    private val groupDao: GroupDao by di.instance()
    private val sessionTypeDao: SessionTypeDao by di.instance()
    private val startTimeDao: StartTimeDao by di.instance()
    private val tableTypeDao: TableTypeDao by di.instance()

    suspend fun meta(): Meta {
        return Meta(
            weekNumber(),
            levels(),
            degrees(),
            forms(),
            tableTypes(),
            groups(),
            startTimes(),
            sessionTypes()
        )
    }

    suspend fun levels(): List<GraduationLevel> {
        return gradLevelDao.all()
    }

    suspend fun degrees(): List<GraduationDegree> {
        return gradDegreeDao.all()
    }

    suspend fun forms(): List<EducationForm> {
        return eduFormDao.all()
    }

    suspend fun tableTypes(): List<TableType> {
        return tableTypeDao.all()
    }

    suspend fun groups(): List<Group> {
        return groupDao.all()
    }

    suspend fun addGroups(vararg groups: Group): Int {
        return groupDao.create(*groups)
    }

    suspend fun startTimes(): List<StartTime> {
        return startTimeDao.all()
    }

    suspend fun sessionTypes(): List<SessionType> {
        return sessionTypeDao.all()
    }


    suspend fun timetables(page: Int?, filters: TimetableFilters): List<Timetable> {
        val offset = offset(page)

        return if (filters == TimetableFilters.Empty) timetableDao.page(offset, limit)
        else timetableDao.filter(filters, offset, limit)
    }

    suspend fun timetable(id: Int): Timetable? {
        return timetableDao.read(id)
    }

    suspend fun timetableBase(id: Int): Timetable? {
        return timetableDao.read(id)
    }

    suspend fun addTimetable(item: Timetable): Int? {
        return timetableDao.create(item)
    }

    suspend fun timetableExists(code: String, type: String): Boolean {
        return timetableDao.filter(
            TimetableFilters(
                groupCode = code,
                typeId = type
            ),
            offset = 0,
            limit = 1
        ).isNotEmpty()
    }

    fun weekNumber(): Week {
        val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
        val thisYearS1 = LocalDate(today.year, Month.SEPTEMBER, 1)

        if (today == thisYearS1) return Week.FIRST

        val lastYearFirstWeekStart = when (today < thisYearS1) {
            true -> LocalDate(today.year - 1, Month.SEPTEMBER, 1)
            else -> thisYearS1
        }.toJavaLocalDate()
            .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
            .toKotlinLocalDate()

        val weeksBetween = lastYearFirstWeekStart.until(today, DateTimeUnit.WEEK)

        return when (weeksBetween % 2) {
            0 -> Week.FIRST
            else -> Week.SECOND
        }
    }

    suspend fun regularSessions(page: Int?, filters: RegularSessionFilters): List<RegularSession> {
        val offset = offset(page)
        return if (filters == RegularSessionFilters.Empty) regularSessionDao.page(offset, limit)
        else regularSessionDao.filter(filters, offset, limit)
    }

    suspend fun datedSessions(page: Int?, filters: DatedSessionFilters): List<DatedSession> {
        val offset = offset(page)
        return if (filters == DatedSessionFilters.Empty) datedSessionDao.page(offset, limit)
        else datedSessionDao.filter(filters, offset, limit)
    }

    suspend fun addRegularSession(item: RegularSession): Int? {
        return regularSessionDao.create(item)
    }

    suspend fun addDatedSession(item: DatedSession): Int? {
        return datedSessionDao.create(item)
    }

    suspend fun addRegularJunction(item: TimetableSession): Unit? {
        return timetableRegularSessionDao.create(item)
    }

    suspend fun addDatedJunction(item: TimetableSession): Unit? {
        return timetableDatedSessionDao.create(item)
    }
}