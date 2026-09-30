package ru.vo1d.web.domain.daybook

import ru.vo1d.web.domain.PageRequest
import ru.vo1d.web.domain.daybook.timetable.session.DatedSession
import ru.vo1d.web.domain.daybook.timetable.session.NewDatedSession
import ru.vo1d.web.domain.daybook.timetable.session.TimetableSession

interface DatedSessionRepository {
    suspend fun find(filters: DatedSessionFilters, page: PageRequest): List<DatedSession>

    /**
     * @return id of the stored session
     * @throws ru.vo1d.web.domain.errors.MissingReferenceException if there is no such session type
     */
    suspend fun add(session: NewDatedSession): Int

    /**
     * Links an existing session to a timetable of the dated format; linking it again changes nothing.
     *
     * @throws ru.vo1d.web.domain.errors.MissingReferenceException if there is no such session or timetable
     */
    suspend fun attach(link: TimetableSession)
}
