package ru.vo1d.web.domain.daybook

import ru.vo1d.web.domain.PageRequest
import ru.vo1d.web.domain.daybook.timetable.Timetable

interface TimetableRepository {
    suspend fun find(filters: TimetableFilters, page: PageRequest): List<Timetable>

    suspend fun get(id: Int): Timetable?

    /** @return id of the stored timetable, or null if the same one already exists */
    suspend fun add(timetable: Timetable): Int?
}
