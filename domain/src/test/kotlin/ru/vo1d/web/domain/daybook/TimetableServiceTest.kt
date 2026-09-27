package ru.vo1d.web.domain.daybook

import kotlinx.coroutines.runBlocking
import ru.vo1d.web.domain.PageRequest
import ru.vo1d.web.domain.daybook.timetable.Timetable
import ru.vo1d.web.domain.daybook.timetable.TimetableFormat
import ru.vo1d.web.domain.daybook.timetable.session.DatedSession
import ru.vo1d.web.domain.daybook.timetable.session.RegularSession
import ru.vo1d.web.domain.daybook.timetable.session.TimetableSession
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class TimetableServiceTest {
    private val timetables = object : TimetableRepository {
        val stored = listOf(
            Timetable(1, "G-1", "main", TimetableFormat.Regular),
            Timetable(2, "G-1", "exams", TimetableFormat.Dated)
        )

        override suspend fun find(filters: TimetableFilters, page: PageRequest) = stored

        override suspend fun get(id: Int) = stored.firstOrNull { it.id == id }

        override suspend fun add(timetable: Timetable): Int? = error("not used")
    }

    private val regular = object : RegularSessionRepository {
        val attached = mutableListOf<TimetableSession>()

        override suspend fun find(filters: RegularSessionFilters, page: PageRequest) = emptyList<RegularSession>()

        override suspend fun add(session: RegularSession): Int? = error("not used")

        override suspend fun attach(link: TimetableSession) {
            attached += link
        }
    }

    private val dated = object : DatedSessionRepository {
        val attached = mutableListOf<TimetableSession>()

        override suspend fun find(filters: DatedSessionFilters, page: PageRequest) = emptyList<DatedSession>()

        override suspend fun add(session: DatedSession): Int? = error("not used")

        override suspend fun attach(link: TimetableSession) {
            attached += link
        }
    }

    private val service = TimetableService(timetables, regular, dated)

    @Test
    fun regularTimetableGetsRegularSession() = runBlocking {
        val link = TimetableSession(timetableId = 1, sessionId = 10)

        assertEquals(1, service.attachSession(link)?.id)
        assertEquals(listOf(link), regular.attached)
        assertTrue(dated.attached.isEmpty())
    }

    @Test
    fun datedTimetableGetsDatedSession() = runBlocking {
        val link = TimetableSession(timetableId = 2, sessionId = 20)

        assertEquals(2, service.attachSession(link)?.id)
        assertEquals(listOf(link), dated.attached)
        assertTrue(regular.attached.isEmpty())
    }

    @Test
    fun missingTimetableAttachesNothing() = runBlocking {
        assertNull(service.attachSession(TimetableSession(timetableId = 3, sessionId = 30)))
        assertTrue(regular.attached.isEmpty())
        assertTrue(dated.attached.isEmpty())
    }
}
