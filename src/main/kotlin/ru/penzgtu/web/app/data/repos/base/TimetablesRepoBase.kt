package ru.penzgtu.web.app.data.repos.base

import ru.penzgtu.web.app.data.dao.*
import ru.penzgtu.web.app.data.delegates.dao
import ru.penzgtu.web.app.data.repos.TimetablesRepo

class TimetablesRepoBase : TimetablesRepo() {
    override val timetableDao by dao<TimetableDao>()
    override val graduationLevelDao by dao<GraduationLevelDao>()
    override val educationFormDao by dao<EducationFormDao>()
    override val groupDao by dao<GroupDao>()
    override val sessionTypeDao by dao<SessionTypeDao>()
    override val tableTypeDao by dao<TableTypeDao>()
    override val timePeriodDao by dao<TimePeriodDao>()
    override val weekOptionDao by dao<WeekOptionDao>()
}