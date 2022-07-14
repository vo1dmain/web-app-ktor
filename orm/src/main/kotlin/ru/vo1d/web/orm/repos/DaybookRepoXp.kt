package ru.vo1d.web.orm.repos

import ru.vo1d.web.data.dao.delegates.dao
import ru.vo1d.web.data.filters.daybook.SessionFilters
import ru.vo1d.web.data.filters.daybook.TimetableFilters
import ru.vo1d.web.data.repos.impl.DaybookRepoImpl
import ru.vo1d.web.entities.daybook.timetable.TimetableModel
import ru.vo1d.web.entities.daybook.timetable.session.SessionModel
import ru.vo1d.web.orm.dao.daybook.group.*
import ru.vo1d.web.orm.dao.daybook.timetable.*
import ru.vo1d.web.orm.db.DbManager

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

    override suspend fun timetables(page: Int?, filtersProducer: TimetableFilters.() -> Unit) = with(dbManager) {
        query(daybookDb) { super.timetables(page, filtersProducer) }
    }

    override suspend fun timetable(id: Int) = with(dbManager) {
        query(daybookDb) { super.timetable(id) }
    }

    override suspend fun addTimetable(item: TimetableModel) = with(dbManager) {
        query(daybookDb) { super.addTimetable(item) }
    }

    override suspend fun sessions(page: Int?, filtersProducer: SessionFilters.() -> Unit) = with(dbManager) {
        query(daybookDb) { super.sessions(page, filtersProducer) }
    }

    override suspend fun addSession(item: SessionModel) = with(dbManager) {
        query(daybookDb) { super.addSession(item) }
    }
}