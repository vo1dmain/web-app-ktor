package ru.vo1d.web.app.data.repos

import ru.vo1d.web.app.data.filters.daybook.SessionFilters
import ru.vo1d.web.app.data.filters.daybook.TimetableFilters
import ru.vo1d.web.entities.daybook.Meta
import ru.vo1d.web.entities.daybook.timetable.TimetableDto
import ru.vo1d.web.entities.daybook.timetable.TimetableModel
import ru.vo1d.web.entities.daybook.timetable.session.SessionModel

interface DaybookRepo : ListRepo {
    suspend fun meta(): Meta

    suspend fun timetables(filters: TimetableFilters, page: Int?): List<TimetableModel>

    suspend fun timetable(id: Int): TimetableDto?

    suspend fun addTimetable(item: TimetableModel): Int

    suspend fun sessions(filters: SessionFilters, page: Int?): List<SessionModel>

    suspend fun addSession(item: SessionModel): Int
}