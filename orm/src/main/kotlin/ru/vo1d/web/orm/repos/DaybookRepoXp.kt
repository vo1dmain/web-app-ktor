package ru.vo1d.web.orm.repos

import ru.vo1d.web.data.dao.delegates.crudDao
import ru.vo1d.web.data.dao.delegates.dao
import ru.vo1d.web.data.filters.daybook.DatedSessionFilters
import ru.vo1d.web.data.filters.daybook.RegularSessionFilters
import ru.vo1d.web.data.filters.daybook.TimetableFilters
import ru.vo1d.web.data.repos.impl.DaybookRepoImpl
import ru.vo1d.web.entities.daybook.group.GroupModel
import ru.vo1d.web.entities.daybook.timetable.TimetableModel
import ru.vo1d.web.entities.daybook.timetable.session.DatedSessionModel
import ru.vo1d.web.entities.daybook.timetable.session.RegularSessionModel
import ru.vo1d.web.entities.daybook.timetable.session.TimetableSessionModel
import ru.vo1d.web.orm.dao.daybook.group.*
import ru.vo1d.web.orm.dao.daybook.timetable.SessionTypeDaoXp
import ru.vo1d.web.orm.dao.daybook.timetable.StartTimeDaoXp
import ru.vo1d.web.orm.dao.daybook.timetable.TimetableDaoXp
import ru.vo1d.web.orm.dao.daybook.timetable.dated.DatedSessionDaoXp
import ru.vo1d.web.orm.dao.daybook.timetable.dated.TimetableDatedSessionDaoXp
import ru.vo1d.web.orm.dao.daybook.timetable.regular.RegularSessionDaoXp
import ru.vo1d.web.orm.dao.daybook.timetable.regular.TimetableRegularSessionDaoXp
import ru.vo1d.web.orm.db.DbManager

class DaybookRepoXp(override val dbManager: DbManager) : DaybookRepoImpl(), XpRepo {
    override val timetableDao by dao<TimetableDaoXp>()

    override val regularSessionDao by crudDao<RegularSessionDaoXp>()
    override val datedSessionDao by crudDao<DatedSessionDaoXp>()

    override val timetableRegularSessionDao by dao<TimetableRegularSessionDaoXp>()
    override val timetableDatedSessionDao by dao<TimetableDatedSessionDaoXp>()

    override val gradLevelDao by crudDao<GradLevelDaoXp>()
    override val gradDegreeDao by crudDao<GradDegreeDaoXp>()
    override val eduFormDao by crudDao<EduFormDaoXp>()
    override val groupDao by crudDao<GroupDaoXp>()

    override val sessionTypeDao by crudDao<SessionTypeDaoXp>()
    override val startTimeDao by crudDao<StartTimeDaoXp>()
    override val tableTypeDao by crudDao<TableTypeDaoXp>()

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

    override suspend fun addGroups(vararg groups: GroupModel) = dbManager {
        query(daybookDb) { super.addGroups(*groups) }
    }

    override suspend fun startTimes() = dbManager {
        query(daybookDb) { super.startTimes() }
    }

    override suspend fun sessionTypes() = dbManager {
        query(daybookDb) { super.sessionTypes() }
    }


    override suspend fun timetables(page: Int?, filtersBuilder: TimetableFilters.Builder.() -> Unit) =
        dbManager {
            query(daybookDb) { super.timetables(page, filtersBuilder) }
        }


    override suspend fun timetable(id: Int) = dbManager {
        query(daybookDb) { super.timetable(id) }
    }

    override suspend fun timetableBase(id: Int) = dbManager {
        query(daybookDb) { super.timetableBase(id) }
    }

    override suspend fun addTimetable(item: TimetableModel): Int? = dbManager {
        query(daybookDb) { super.addTimetable(item) }
    }

    override suspend fun timetableExists(code: String, type: String) = dbManager {
        query(daybookDb) { super.timetableExists(code, type) }
    }

    override suspend fun regularSessions(
        page: Int?,
        filtersBuilder: RegularSessionFilters.Builder.() -> Unit
    ) = dbManager {
        query(daybookDb) { super.regularSessions(page, filtersBuilder) }
    }

    override suspend fun datedSessions(
        page: Int?,
        filtersBuilder: DatedSessionFilters.Builder.() -> Unit
    ) = dbManager {
        query(daybookDb) { super.datedSessions(page, filtersBuilder) }
    }

    override suspend fun addRegularSession(item: RegularSessionModel) = dbManager {
        query(daybookDb) { super.addRegularSession(item) }
    }

    override suspend fun addDatedSession(item: DatedSessionModel) = dbManager {
        query(daybookDb) { super.addDatedSession(item) }
    }


    override suspend fun addRegularJunction(item: TimetableSessionModel) = dbManager {
        query(daybookDb) { super.addRegularJunction(item) }
    }

    override suspend fun addDatedJunction(item: TimetableSessionModel) = dbManager {
        query(daybookDb) { super.addDatedJunction(item) }
    }
}