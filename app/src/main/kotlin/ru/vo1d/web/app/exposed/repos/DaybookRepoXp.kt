package ru.vo1d.web.app.exposed.repos

import ru.vo1d.web.app.data.dao.delegates.dao
import ru.vo1d.web.app.data.filters.daybook.TimetableFilters
import ru.vo1d.web.app.data.repos.impl.DaybookRepoImpl
import ru.vo1d.web.app.exposed.dao.daybook.group.*
import ru.vo1d.web.app.exposed.dao.daybook.timetable.*
import ru.vo1d.web.app.exposed.db.DbManager

class DaybookRepoXp(private val dbManager: DbManager) : DaybookRepoImpl() {
    override val timetableDao by dao<TimetableDaoXp>()
    override val sessionDao by dao<SessionDaoXp>()
    override val dayDao by dao<DayDaoXp>()

    override val gradLevelDao by dao<GradLevelDaoXp>()
    override val gradDegreeDao by dao<GradDegreeDaoXp>()
    override val eduFormDao by dao<EduFormDaoXp>()
    override val groupDao by dao<GroupDaoXp>()

    override val sessionTypeDao by dao<SessionTypeDaoXp>()
    override val timePeriodDao by dao<TimePeriodDaoXp>()
    override val weekOptionDao by dao<WeekOptionDaoXp>()
    override val tableTypeDao by dao<TableTypeDaoXp>()

    override suspend fun meta() = with(dbManager) {
        query(daybookDb) { super.meta() }
    }

    override suspend fun timetables(filters: TimetableFilters?, page: Int?) = with(dbManager) {
        query(daybookDb) { super.timetables(filters, page) }
    }

    override suspend fun timetable(id: Int) = with(dbManager) {
        query(daybookDb) { super.timetable(id) }
    }
}