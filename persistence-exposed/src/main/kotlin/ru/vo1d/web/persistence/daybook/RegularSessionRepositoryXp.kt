package ru.vo1d.web.persistence.daybook

import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.andWhere
import org.jetbrains.exposed.v1.jdbc.insertIgnore
import org.jetbrains.exposed.v1.jdbc.insertAndGetId
import org.jetbrains.exposed.v1.jdbc.selectAll
import ru.vo1d.web.domain.PageRequest
import ru.vo1d.web.domain.daybook.RegularSessionFilters
import ru.vo1d.web.domain.daybook.RegularSessionRepository
import ru.vo1d.web.domain.daybook.timetable.session.NewRegularSession
import ru.vo1d.web.domain.daybook.timetable.session.RegularSession
import ru.vo1d.web.domain.daybook.timetable.session.TimetableSession
import ru.vo1d.web.persistence.XpRepository
import ru.vo1d.web.persistence.context.DbContext

class RegularSessionRepositoryXp(ctx: DbContext) : XpRepository(ctx.daybook), RegularSessionRepository {
    override suspend fun find(filters: RegularSessionFilters, page: PageRequest): List<RegularSession> = query {
        val query = RegularSessions.selectAll().apply {
            filters.timetableId?.let {
                adjustColumnSet { innerJoin(TimetableRegularSessions) }
                andWhere { TimetableRegularSessions.timetableId eq it }
            }
            filters.subject?.let { andWhere { RegularSessions.subject eq it } }
            filters.instructor?.let { andWhere { RegularSessions.instructor eq it } }
            filters.place?.let { andWhere { RegularSessions.place eq it } }
            filters.dayOfWeek?.let { andWhere { RegularSessions.dayOfWeek eq it } }
            filters.time?.let { andWhere { RegularSessions.time eq it } }
            filters.typeId?.let { andWhere { RegularSessions.typeId eq it } }
            filters.weekOption?.let { andWhere { RegularSessions.weekOption eq it } }
            orderBy(RegularSessions.id)
            limit(page.size)
            offset(page.offset)
        }

        // dayOfWeek is stored by name, so SQL can't order by it; only the page itself is sorted
        RegularSessionEntity.wrapRows(query)
            .sortedWith(compareBy({ it.dayOfWeek }, { it.time }))
            .map(RegularSessionEntity::toDomain)
    }

    override suspend fun add(session: NewRegularSession): Int = query {
        RegularSessions.insertAndGetId { it.mapItem(session) }.value
    }

    override suspend fun attach(link: TimetableSession) = query {
        TimetableRegularSessions.insertIgnore {
            it[timetableId] = link.timetableId
            it[sessionId] = link.sessionId
        }
        Unit
    }
}
