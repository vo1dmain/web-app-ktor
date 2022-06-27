package ru.penzgtu.web.app.exposed.repos

import ru.penzgtu.web.app.data.dao.*
import ru.penzgtu.web.app.data.dao.delegates.dao
import ru.penzgtu.web.app.data.repos.DaybookRepo
import ru.penzgtu.web.app.exposed.dao.timetables.TimetableDaoXp

class DaybookRepoXp : DaybookRepo() {
    override val timetableDao by dao<TimetableDaoXp>()
    override val graduationLevelDao by dao<GraduationLevelDao>()
    override val educationFormDao by dao<EducationFormDao>()
    override val groupDao by dao<GroupDao>()
    override val sessionTypeDao by dao<SessionTypeDao>()
    override val tableTypeDao by dao<TableTypeDao>()
    override val timePeriodDao by dao<TimePeriodDao>()
    override val weekOptionDao by dao<WeekOptionDao>()
}