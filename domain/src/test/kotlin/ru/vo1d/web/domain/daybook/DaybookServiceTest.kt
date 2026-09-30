package ru.vo1d.web.domain.daybook

import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.toInstant
import ru.vo1d.web.domain.daybook.group.*
import ru.vo1d.web.domain.daybook.timetable.session.SessionType
import ru.vo1d.web.domain.daybook.timetable.week.Week
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Clock
import kotlin.time.Instant

class DaybookServiceTest {
    private val zone = TimeZone.of("Europe/Moscow")

    private val noReference = object : ReferenceRepository {
        override suspend fun levels() = emptyList<GraduationLevel>()
        override suspend fun degrees() = emptyList<GraduationDegree>()
        override suspend fun forms() = emptyList<EducationForm>()
        override suspend fun tableTypes() = emptyList<TableType>()
        override suspend fun sessionTypes() = emptyList<SessionType>()
        override suspend fun groups() = emptyList<Group>()
    }

    private fun weekAt(now: Instant): Week {
        val clock = object : Clock {
            override fun now() = now
        }
        return DaybookService(noReference, zone, clock).week()
    }

    private fun weekOn(date: LocalDate) = weekAt(date.atTime(12, 0).toInstant(zone))

    // 2026-09-01 is a Tuesday, so the first week starts on Monday 2026-08-31

    @Test
    fun septemberFirstIsFirstWeek() {
        assertEquals(Week.FIRST, weekOn(LocalDate(2026, 9, 1)))
    }

    @Test
    fun weeksAlternateFromMondayOfSeptemberFirst() {
        assertEquals(Week.FIRST, weekOn(LocalDate(2026, 9, 6)))
        assertEquals(Week.SECOND, weekOn(LocalDate(2026, 9, 7)))
        assertEquals(Week.FIRST, weekOn(LocalDate(2026, 9, 14)))
    }

    @Test
    fun beforeSeptemberCountsFromLastYear() {
        // 2025-09-01 is a Monday; 2026-08-30 is 51 weeks later
        assertEquals(Week.SECOND, weekOn(LocalDate(2026, 8, 30)))
    }

    @Test
    fun weekTurnsOverAtMidnightOfTheDaybookZone() {
        // Sunday 21:30 UTC is already Monday 00:30 in Moscow
        assertEquals(Week.SECOND, weekAt(Instant.parse("2026-09-06T21:30:00Z")))
    }
}
