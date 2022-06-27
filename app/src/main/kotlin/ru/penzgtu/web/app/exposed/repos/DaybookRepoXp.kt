package ru.penzgtu.web.app.exposed.repos

import ru.penzgtu.web.app.data.dao.delegates.dao
import ru.penzgtu.web.app.data.filters.daybook.TimetableFilters
import ru.penzgtu.web.app.data.repos.DaybookRepo
import ru.penzgtu.web.app.exposed.dao.daybook.*
import ru.penzgtu.web.app.exposed.orm.db.DbManager

class DaybookRepoXp(private val dbManager: DbManager) : DaybookRepo() {
    override val timetableDao by dao<TimetableDaoXp>()
    override val sessionTypeDao by dao<SessionTypeDaoXp>()
    override val tableTypeDao by dao<TableTypeDaoXp>()
    override val timePeriodDao by dao<TimePeriodDaoXp>()
    override val weekOptionDao by dao<WeekOptionDaoXp>()
    override val graduationLevelDao by dao<GraduationLevelDaoXp>()
    override val educationFormDao by dao<EducationFormDaoXp>()
    override val groupDao by dao<GroupDaoXp>()

    override suspend fun meta() = with(dbManager) {
        query(timetableDb) { super.meta() }
    }

    override suspend fun timetables(filters: TimetableFilters?, page: Int?) = with(dbManager) {
        query(timetableDb) { super.timetables(filters, page) }
    }

    override suspend fun timetable(id: Int) = with(dbManager) {
        query(timetableDb) { super.timetable(id) }
    }
}