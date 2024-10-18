package ru.vo1d.web.exposed.repos

import ru.vo1d.web.data.dao.delegates.crudDao
import ru.vo1d.web.data.dao.delegates.dao
import ru.vo1d.web.data.filters.daybook.DatedSessionFilters
import ru.vo1d.web.data.filters.daybook.RegularSessionFilters
import ru.vo1d.web.data.filters.daybook.TimetableFilters
import ru.vo1d.web.data.repos.base.DaybookRepoBase
import ru.vo1d.web.entities.daybook.group.Group
import ru.vo1d.web.entities.daybook.timetable.Timetable
import ru.vo1d.web.entities.daybook.timetable.session.DatedSession
import ru.vo1d.web.entities.daybook.timetable.session.RegularSession
import ru.vo1d.web.entities.daybook.timetable.session.TimetableSession
import ru.vo1d.web.exposed.context.DbContext
import ru.vo1d.web.exposed.dao.daybook.group.*
import ru.vo1d.web.exposed.dao.daybook.timetable.SessionTypeDaoXp
import ru.vo1d.web.exposed.dao.daybook.timetable.StartTimeDaoXp
import ru.vo1d.web.exposed.dao.daybook.timetable.TimetableDaoXp
import ru.vo1d.web.exposed.dao.daybook.timetable.dated.DatedSessionDaoXp
import ru.vo1d.web.exposed.dao.daybook.timetable.dated.TimetableDatedSessionDaoXp
import ru.vo1d.web.exposed.dao.daybook.timetable.regular.RegularSessionDaoXp
import ru.vo1d.web.exposed.dao.daybook.timetable.regular.TimetableRegularSessionDaoXp

class DaybookRepoXp(override val dbContext: DbContext) : DaybookRepoBase(), XpRepo {
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

    override suspend fun meta() = dbContext {
        query(daybookDb) { super.meta() }
    }

    override suspend fun levels() = dbContext {
        query(daybookDb) { super.levels() }
    }

    override suspend fun degrees() = dbContext {
        query(daybookDb) { super.degrees() }
    }

    override suspend fun forms() = dbContext {
        query(daybookDb) { super.forms() }
    }

    override suspend fun tableTypes() = dbContext {
        query(daybookDb) { super.tableTypes() }
    }

    override suspend fun groups() = dbContext {
        query(daybookDb) { super.groups() }
    }

    override suspend fun addGroups(vararg groups: Group) = dbContext {
        query(daybookDb) { super.addGroups(*groups) }
    }

    override suspend fun startTimes() = dbContext {
        query(daybookDb) { super.startTimes() }
    }

    override suspend fun sessionTypes() = dbContext {
        query(daybookDb) { super.sessionTypes() }
    }


    override suspend fun timetables(page: Int?, filtersBuilder: TimetableFilters.Builder.() -> Unit) =
        dbContext {
            query(daybookDb) { super.timetables(page, filtersBuilder) }
        }


    override suspend fun timetable(id: Int) = dbContext {
        query(daybookDb) { super.timetable(id) }
    }

    override suspend fun timetableBase(id: Int) = dbContext {
        query(daybookDb) { super.timetableBase(id) }
    }

    override suspend fun addTimetable(item: Timetable): Int? = dbContext {
        query(daybookDb) { super.addTimetable(item) }
    }

    override suspend fun timetableExists(code: String, type: String) = dbContext {
        query(daybookDb) { super.timetableExists(code, type) }
    }

    override suspend fun regularSessions(
        page: Int?,
        filtersBuilder: RegularSessionFilters.Builder.() -> Unit
    ) = dbContext {
        query(daybookDb) { super.regularSessions(page, filtersBuilder) }
    }

    override suspend fun datedSessions(
        page: Int?,
        filtersBuilder: DatedSessionFilters.Builder.() -> Unit
    ) = dbContext {
        query(daybookDb) { super.datedSessions(page, filtersBuilder) }
    }

    override suspend fun addRegularSession(item: RegularSession) = dbContext {
        query(daybookDb) { super.addRegularSession(item) }
    }

    override suspend fun addDatedSession(item: DatedSession) = dbContext {
        query(daybookDb) { super.addDatedSession(item) }
    }


    override suspend fun addRegularJunction(item: TimetableSession) = dbContext {
        query(daybookDb) { super.addRegularJunction(item) }
    }

    override suspend fun addDatedJunction(item: TimetableSession) = dbContext {
        query(daybookDb) { super.addDatedJunction(item) }
    }
}