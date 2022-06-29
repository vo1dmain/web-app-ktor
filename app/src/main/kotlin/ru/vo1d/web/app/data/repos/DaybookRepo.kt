package ru.vo1d.web.app.data.repos

import ru.vo1d.web.app.data.filters.daybook.TimetableFilters
import ru.vo1d.web.entities.daybook.Meta
import ru.vo1d.web.entities.daybook.timetable.TimetableDto
import ru.vo1d.web.entities.daybook.timetable.TimetableModel

interface DaybookRepo : ListRepo {
    suspend fun meta(): Meta

    suspend fun timetables(filters: TimetableFilters?, page: Int?): List<TimetableModel>

    suspend fun timetable(id: Int): TimetableDto?
}