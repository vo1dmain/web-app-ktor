package ru.penzgtu.web.app.repos

import ru.penzgtu.web.app.data.timetables.meta.MetaRoot

interface TimetablesRepo {
    suspend fun meta(): MetaRoot {
        TODO("Not yet implemented")
    }
}