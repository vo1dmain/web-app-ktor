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

class DaybookRepoXp(override val dbManager: DbManager) : DaybookRepoImpl(), XpRepo {
    override val timetableDao by dao<TimetableDaoXp>()
    override val sessionDao by dao<SessionDaoXp>()
    override val dayDao by dao<DayDaoXp>()

    override val gradLevelDao by dao<GradLevelDaoXp>()
    override val gradDegreeDao by dao<GradDegreeDaoXp>()
    override val eduFormDao by dao<EduFormDaoXp>()
    override val groupDao by dao<GroupDaoXp>()

    override val sessionTypeDao by dao<SessionTypeDaoXp>()
    override val startTimeDao by dao<StartTimeDaoXp>()
    override val weekOptionDao by dao<WeekOptionDaoXp>()
    override val tableTypeDao by dao<TableTypeDaoXp>()

    override suspend fun meta() = dbManager {
        query(daybookDb) { super.meta() }
    }

    override suspend fun levels() = dbManager {
        query(daybookDb) { super.levels() }
    }

    override suspend fun degrees() = dbManager {
        query(daybookDb) { super.degrees() }
    }

    override suspend fun forms() = dbManager {
        query(daybookDb) { super.forms() }
    }

    override suspend fun tableTypes() = dbManager {
        query(daybookDb) { super.tableTypes() }
    }

    override suspend fun groups() = dbManager {
        query(daybookDb) { super.groups() }
    }

    override suspend fun startTimes() = dbManager {
        query(daybookDb) { super.startTimes() }
    }

    override suspend fun sessionTypes() = dbManager {
        query(daybookDb) { super.sessionTypes() }
    }

    override suspend fun weekOptions() = dbManager {
        query(daybookDb) { super.weekOptions() }
    }


    override suspend fun timetables(page: Int?, filtersBuilder: TimetableFilters.Builder.() -> Unit) =
        dbManager {
            query(daybookDb) { super.timetables(page, filtersBuilder) }
        }

    override suspend fun timetable(id: Int) = dbManager {
        query(daybookDb) { super.timetable(id) }
    }

    override suspend fun addTimetable(item: TimetableModel) = dbManager {
        query(daybookDb) { super.addTimetable(item) }
    }


    override suspend fun sessions(page: Int?, filtersBuilder: SessionFilters.Builder.() -> Unit) =
        dbManager {
            query(daybookDb) { super.sessions(page, filtersBuilder) }
        }

    override suspend fun addSession(item: SessionModel) = dbManager {
        query(daybookDb) { super.addSession(item) }
    }
}