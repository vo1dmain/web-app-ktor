package ru.vo1d.web.domain.daybook

import ru.vo1d.web.domain.PageRequest
import ru.vo1d.web.domain.daybook.timetable.session.DatedSession
import ru.vo1d.web.domain.daybook.timetable.session.NewDatedSession
import ru.vo1d.web.domain.daybook.timetable.session.TimetableSession

interface DatedSessionRepository {
    suspend fun find(filters: DatedSessionFilters, page: PageRequest): List<DatedSession>

    /** @return id of the stored session, or null if the same one already exists */
    suspend fun add(session: NewDatedSession): Int?

    /** Links an existing session to a timetable of the dated format. */
    suspend fun attach(link: TimetableSession)
}
