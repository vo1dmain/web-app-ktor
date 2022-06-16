package ru.penzgtu.web.app.data.repos.base

import ru.penzgtu.web.app.data.dao.TimetableDao
import ru.penzgtu.web.app.data.delegates.dao
import ru.penzgtu.web.app.data.repos.TimetablesRepo

class TimetablesRepoBase : TimetablesRepo() {
    override val timetableDao by dao<TimetableDao>()
}