package ru.penzgtu.web.app.repos

import ru.penzgtu.web.app.data.timetables.entities.Timetable
import ru.penzgtu.web.app.data.timetables.entities.TimetableView
import ru.penzgtu.web.app.data.timetables.meta.Meta
import ru.penzgtu.web.app.data.timetables.meta.Week

interface TimetablesRepo {
    suspend fun meta(): Meta

    suspend fun list(groupCode: String?, typeId: Int?): List<TimetableView>

    suspend fun item(id: Int): Timetable?

    suspend fun week(): Week?
}