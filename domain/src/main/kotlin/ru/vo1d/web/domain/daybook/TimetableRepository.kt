package ru.vo1d.web.domain.daybook

import ru.vo1d.web.domain.PageRequest
import ru.vo1d.web.domain.daybook.timetable.NewTimetable
import ru.vo1d.web.domain.daybook.timetable.Timetable

interface TimetableRepository {
    suspend fun find(filters: TimetableFilters, page: PageRequest): List<Timetable>

    suspend fun get(id: Int): Timetable?

    /**
     * @return id of the stored timetable
     * @throws ru.vo1d.web.domain.errors.AlreadyExistsException if the group already has such a timetable
     * @throws ru.vo1d.web.domain.errors.MissingReferenceException if there is no such group or table type
     */
    suspend fun add(timetable: NewTimetable): Int
}
