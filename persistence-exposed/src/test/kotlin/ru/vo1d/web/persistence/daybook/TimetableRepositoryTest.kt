package ru.vo1d.web.persistence.daybook

import kotlinx.coroutines.runBlocking
import ru.vo1d.web.domain.PageRequest
import ru.vo1d.web.domain.daybook.TimetableFilters
import ru.vo1d.web.domain.daybook.timetable.NewTimetable
import ru.vo1d.web.domain.daybook.timetable.TimetableFormat
import ru.vo1d.web.domain.errors.AlreadyExistsException
import ru.vo1d.web.domain.errors.MissingReferenceException
import ru.vo1d.web.persistence.testDatabases
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull

class TimetableRepositoryTest {
    private val timetables = TimetableRepositoryXp(testDatabases())

    @Test
    fun addedTimetableCanBeRead() = runBlocking<Unit> {
        val id = timetables.add(NewTimetable("ИВТ-21", "tt", TimetableFormat.Regular))

        val stored = timetables.get(id)
        assertEquals("ИВТ-21", stored?.groupCode)
        assertEquals(TimetableFormat.Regular, stored?.format)
        assertNull(timetables.get(id + 1))
    }

    @Test
    fun sameTimetableTwiceIsRejected() = runBlocking<Unit> {
        timetables.add(NewTimetable("ИВТ-21", "tt", TimetableFormat.Regular))

        assertFailsWith<AlreadyExistsException> {
            timetables.add(NewTimetable("ИВТ-21", "tt", TimetableFormat.Regular))
        }
    }

    @Test
    fun unknownGroupOrTypeIsMissingReference() = runBlocking<Unit> {
        assertFailsWith<MissingReferenceException> {
            timetables.add(NewTimetable("НЕТ-00", "tt", TimetableFormat.Regular))
        }
        assertFailsWith<MissingReferenceException> {
            timetables.add(NewTimetable("ИВТ-21", "nope", TimetableFormat.Regular))
        }
    }

    @Test
    fun filtersMatchExactly() = runBlocking<Unit> {
        timetables.add(NewTimetable("ИВТ-21", "tt", TimetableFormat.Regular))
        timetables.add(NewTimetable("ИВТ-21", "att", TimetableFormat.Dated))
        timetables.add(NewTimetable("ИВТ-М-24", "tt", TimetableFormat.Regular))

        val page = PageRequest()
        assertEquals(2, timetables.find(TimetableFilters(groupCode = "ИВТ-21"), page).size)
        assertEquals(0, timetables.find(TimetableFilters(groupCode = "ИВТ%"), page).size)
        assertEquals(1, timetables.find(TimetableFilters(format = TimetableFormat.Dated), page).size)
        assertEquals(3, timetables.find(TimetableFilters(), page).size)
        assertEquals(1, timetables.find(TimetableFilters(), PageRequest(number = 2, size = 2)).size)
    }
}
