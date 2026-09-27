package ru.vo1d.web.domain.daybook

import ru.vo1d.web.domain.PageRequest
import ru.vo1d.web.domain.daybook.timetable.session.NewRegularSession
import ru.vo1d.web.domain.daybook.timetable.session.RegularSession
import ru.vo1d.web.domain.daybook.timetable.session.TimetableSession

interface RegularSessionRepository {
    suspend fun find(filters: RegularSessionFilters, page: PageRequest): List<RegularSession>

    /** @return id of the stored session, or null if the same one already exists */
    suspend fun add(session: NewRegularSession): Int?

    /** Links an existing session to a timetable of the regular format. */
    suspend fun attach(link: TimetableSession)
}
