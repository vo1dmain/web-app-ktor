package ru.vo1d.web.persistence.daybook

import kotlinx.coroutines.runBlocking
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalTime
import ru.vo1d.web.domain.PageRequest
import ru.vo1d.web.domain.daybook.DatedSessionFilters
import ru.vo1d.web.domain.daybook.RegularSessionFilters
import ru.vo1d.web.domain.daybook.timetable.NewTimetable
import ru.vo1d.web.domain.daybook.timetable.TimetableFormat
import ru.vo1d.web.domain.daybook.timetable.session.NewDatedSession
import ru.vo1d.web.domain.daybook.timetable.session.NewRegularSession
import ru.vo1d.web.domain.daybook.timetable.session.TimetableSession
import ru.vo1d.web.domain.daybook.timetable.week.WeekOption
import ru.vo1d.web.domain.errors.MissingReferenceException
import ru.vo1d.web.persistence.testDatabases
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Instant

class SessionRepositoryTest {
    private val databases = testDatabases()
    private val timetables = TimetableRepositoryXp(databases)
    private val regular = RegularSessionRepositoryXp(databases)
    private val dated = DatedSessionRepositoryXp(databases)

    private fun regularOn(day: DayOfWeek, time: String) =
        NewRegularSession("Матан", "Иванов", "101", 1, null, day, LocalTime.parse(time), WeekOption.Both)

    private fun datedAt(dateTime: String) =
        NewDatedSession("Экзамен", "Иванов", "101", 5, 90.minutes, Instant.parse(dateTime))

    @Test
    fun regularSessionsAreOrderedByWeekDayAndTime() = runBlocking<Unit> {
        regular.add(regularOn(DayOfWeek.SUNDAY, "10:00"))
        regular.add(regularOn(DayOfWeek.FRIDAY, "08:00"))
        regular.add(regularOn(DayOfWeek.MONDAY, "11:40"))
        regular.add(regularOn(DayOfWeek.WEDNESDAY, "08:00"))
        regular.add(regularOn(DayOfWeek.MONDAY, "08:00"))

        val order = regular.find(RegularSessionFilters(), PageRequest()).map { it.dayOfWeek to it.time.toString() }

        assertEquals(
            listOf(
                DayOfWeek.MONDAY to "08:00",
                DayOfWeek.MONDAY to "11:40",
                DayOfWeek.WEDNESDAY to "08:00",
                DayOfWeek.FRIDAY to "08:00",
                DayOfWeek.SUNDAY to "10:00"
            ),
            order
        )
    }

    @Test
    fun omittedDurationIsTheDefault() = runBlocking<Unit> {
        regular.add(regularOn(DayOfWeek.MONDAY, "08:00"))

        assertEquals(45.minutes, regular.find(RegularSessionFilters(), PageRequest()).single().duration)
    }

    @Test
    fun filtersByTimetableDayAndTime() = runBlocking<Unit> {
        val timetable = timetables.add(NewTimetable("ИВТ-21", "tt", TimetableFormat.Regular))
        val attached = regular.add(regularOn(DayOfWeek.TUESDAY, "09:50"))
        regular.add(regularOn(DayOfWeek.TUESDAY, "13:30"))
        regular.attach(TimetableSession(timetable, attached))
        regular.attach(TimetableSession(timetable, attached))

        val page = PageRequest()
        assertEquals(listOf(attached), regular.find(RegularSessionFilters(timetableId = timetable), page).map { it.id })
        assertEquals(2, regular.find(RegularSessionFilters(dayOfWeek = DayOfWeek.TUESDAY), page).size)
        assertEquals(1, regular.find(RegularSessionFilters(time = LocalTime(13, 30)), page).size)
    }

    @Test
    fun missingReferencesAreReported() = runBlocking<Unit> {
        val timetable = timetables.add(NewTimetable("ИВТ-21", "tt", TimetableFormat.Regular))

        assertFailsWith<MissingReferenceException> {
            regular.add(regularOn(DayOfWeek.MONDAY, "08:00").copy(typeId = 999))
        }
        assertFailsWith<MissingReferenceException> {
            regular.attach(TimetableSession(timetable, 999))
        }
    }

    @Test
    fun datedSessionsAreOrderedByTime() = runBlocking<Unit> {
        dated.add(datedAt("2026-01-20T07:00:00Z"))
        val first = dated.add(datedAt("2026-01-12T07:00:00Z"))

        val sessions = dated.find(DatedSessionFilters(), PageRequest())
        assertEquals(first, sessions.first().id)
        assertEquals(90.minutes, sessions.first().duration)
        assertEquals(1, dated.find(DatedSessionFilters(dateTime = Instant.parse("2026-01-20T07:00:00Z")), PageRequest()).size)
    }
}
