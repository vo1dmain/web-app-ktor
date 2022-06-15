package ru.penzgtu.web.app.repos

import ru.penzgtu.web.app.data.timetables.entities.Timetable
import ru.penzgtu.web.app.data.timetables.meta.Meta

interface TimetablesRepo {
    suspend fun meta(): Meta

    suspend fun item(id: Int, tableTypeId: Int): Timetable?
}