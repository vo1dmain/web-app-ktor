package ru.vo1d.web.persistence.daybook

import kotlinx.coroutines.runBlocking
import kotlinx.datetime.DayOfWeek
import ru.vo1d.web.domain.PageRequest
import ru.vo1d.web.domain.daybook.DatedSessionFilters
import ru.vo1d.web.domain.daybook.RegularSessionFilters
import ru.vo1d.web.domain.daybook.TimetableFilters
import ru.vo1d.web.domain.daybook.timetable.TimetableFormat
import ru.vo1d.web.persistence.testDatabases
import kotlin.io.path.toPath
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.minutes

class ImportedDataTest {
    private val sample = javaClass.getResource("/import/sample.json")!!.toURI().toPath()

    private val databases = testDatabases(demoData = false, importFile = sample)
    private val timetables = TimetableRepositoryXp(databases)
    private val regular = RegularSessionRepositoryXp(databases)
    private val dated = DatedSessionRepositoryXp(databases)

    @Test
    fun timetablesComeWithTheirSessions() = runBlocking<Unit> {
        val page = PageRequest()
        val (classes, attestation) = timetables.find(TimetableFilters(groupCode = "ТСТ-01"), page)
            .partition { it.format == TimetableFormat.Regular }

        val sessions = regular.find(RegularSessionFilters(timetableId = classes.single().id), page)
        assertEquals(listOf(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY), sessions.map { it.dayOfWeek })
        assertEquals(90.minutes, sessions.last().duration)

        assertEquals(1, dated.find(DatedSessionFilters(timetableId = attestation.single().id), page).size)
    }

    @Test
    fun longTextIsCutToTheColumns() = runBlocking<Unit> {
        val monday = regular.find(RegularSessionFilters(dayOfWeek = DayOfWeek.MONDAY), PageRequest()).single()

        assertEquals(160, monday.subject.length)
        assertEquals(32, monday.place.length)
    }

    @Test
    fun loadingAgainAddsNothing() = runBlocking<Unit> {
        databases.init()

        val page = PageRequest()
        assertEquals(2, timetables.find(TimetableFilters(), page).size)
        assertEquals(2, regular.find(RegularSessionFilters(), page).size)
        assertEquals(1, dated.find(DatedSessionFilters(), page).size)
    }
}
