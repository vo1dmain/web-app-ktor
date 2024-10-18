package ru.vo1d.web.data.repos

import kotlinx.datetime.*
import ru.vo1d.web.data.filters.daybook.DatedSessionFilters
import ru.vo1d.web.data.filters.daybook.RegularSessionFilters
import ru.vo1d.web.data.filters.daybook.TimetableFilters
import ru.vo1d.web.entities.daybook.Meta
import ru.vo1d.web.entities.daybook.group.Group
import ru.vo1d.web.entities.daybook.group.GroupWithTypes
import ru.vo1d.web.entities.daybook.group.degree.GraduationDegree
import ru.vo1d.web.entities.daybook.group.form.EducationForm
import ru.vo1d.web.entities.daybook.group.level.GraduationLevel
import ru.vo1d.web.entities.daybook.group.type.TableType
import ru.vo1d.web.entities.daybook.timetable.Timetable
import ru.vo1d.web.entities.daybook.timetable.TimetableWithSessions
import ru.vo1d.web.entities.daybook.timetable.session.*
import ru.vo1d.web.entities.daybook.timetable.time.StartTime
import ru.vo1d.web.entities.daybook.timetable.week.Week
import java.time.temporal.TemporalAdjusters

interface DaybookRepo : ListRepo {
    suspend fun meta(): Meta

    suspend fun levels(): List<GraduationLevel>

    suspend fun degrees(): List<GraduationDegree>

    suspend fun forms(): List<EducationForm>

    suspend fun tableTypes(): List<TableType>

    suspend fun groups(): List<GroupWithTypes>

    suspend fun addGroups(vararg groups: Group): Int

    suspend fun startTimes(): List<StartTime>

    suspend fun sessionTypes(): List<SessionType>

    suspend fun weekNumber(): Week {
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


    suspend fun timetables(page: Int?, filters: TimetableFilters): List<Timetable>

    suspend fun timetable(id: Int): TimetableWithSessions<Session>?

    suspend fun timetableBase(id: Int): Timetable?

    suspend fun addTimetable(item: Timetable): Int?

    suspend fun timetableExists(code: String, type: String): Boolean

    suspend fun regularSessions(
        page: Int?,
        filters: RegularSessionFilters
    ): List<RegularSession>

    suspend fun datedSessions(
        page: Int?,
        filters: DatedSessionFilters
    ): List<DatedSession>


    suspend fun addRegularSession(item: RegularSession): Int?

    suspend fun addDatedSession(item: DatedSession): Int?


    suspend fun addRegularJunction(item: TimetableSession): Unit?

    suspend fun addDatedJunction(item: TimetableSession): Unit?
}