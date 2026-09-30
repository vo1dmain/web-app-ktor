package ru.vo1d.web.domain.daybook

import ru.vo1d.web.domain.daybook.timetable.Timetable
import ru.vo1d.web.domain.daybook.timetable.TimetableFormat
import ru.vo1d.web.domain.daybook.timetable.session.TimetableSession

class TimetableService(
    private val timetables: TimetableRepository,
    private val regularSessions: RegularSessionRepository,
    private val datedSessions: DatedSessionRepository
) {
    /**
     * Links a session to a timetable; the timetable's format decides which kind of session it holds.
     *
     * @return the timetable the session was attached to, or null if there is no such timetable
     */
    suspend fun attachSession(link: TimetableSession): Timetable? {
        val timetable = timetables.get(link.timetableId) ?: return null

        when (timetable.format) {
            TimetableFormat.Regular -> regularSessions.attach(link)
            TimetableFormat.Dated -> datedSessions.attach(link)
        }

        return timetable
    }
}
