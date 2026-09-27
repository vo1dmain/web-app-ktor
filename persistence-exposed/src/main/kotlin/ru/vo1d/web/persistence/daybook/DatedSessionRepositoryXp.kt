package ru.vo1d.web.persistence.daybook

import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.andWhere
import org.jetbrains.exposed.v1.jdbc.insertIgnore
import org.jetbrains.exposed.v1.jdbc.insertIgnoreAndGetId
import org.jetbrains.exposed.v1.jdbc.selectAll
import ru.vo1d.web.domain.PageRequest
import ru.vo1d.web.domain.daybook.DatedSessionFilters
import ru.vo1d.web.domain.daybook.DatedSessionRepository
import ru.vo1d.web.domain.daybook.timetable.session.DatedSession
import ru.vo1d.web.domain.daybook.timetable.session.TimetableSession
import ru.vo1d.web.persistence.XpRepository
import ru.vo1d.web.persistence.context.DbContext
import ru.vo1d.web.persistence.entities.daybook.timetable.DatedSessionEntity
import ru.vo1d.web.persistence.entities.daybook.timetable.DatedSessions
import ru.vo1d.web.persistence.entities.daybook.timetable.TimetableDatedSessions
import ru.vo1d.web.persistence.mappers.mapItem
import ru.vo1d.web.persistence.mappers.toDomain

class DatedSessionRepositoryXp(ctx: DbContext) : XpRepository(ctx.daybook), DatedSessionRepository {
    override suspend fun find(filters: DatedSessionFilters, page: PageRequest): List<DatedSession> = query {
        val query = DatedSessions.selectAll().apply {
            filters.timetableId?.let {
                adjustColumnSet { innerJoin(TimetableDatedSessions) }
                andWhere { TimetableDatedSessions.timetableId eq it }
            }
            filters.subject?.let { andWhere { DatedSessions.subject eq it } }
            filters.instructor?.let { andWhere { DatedSessions.instructor eq it } }
            filters.place?.let { andWhere { DatedSessions.place eq it } }
            filters.typeId?.let { andWhere { DatedSessions.typeId eq it } }
            filters.dateTime?.let { andWhere { DatedSessions.dateTime eq it } }
            orderBy(DatedSessions.dateTime)
            limit(page.size)
            offset(page.offset)
        }

        DatedSessionEntity.wrapRows(query).map(DatedSessionEntity::toDomain)
    }

    override suspend fun add(session: DatedSession): Int? = query {
        DatedSessions.insertIgnoreAndGetId { it.mapItem(session) }?.value
    }

    override suspend fun attach(link: TimetableSession) = query {
        TimetableDatedSessions.insertIgnore {
            it[timetableId] = link.timetableId
            it[sessionId] = link.sessionId
        }
        Unit
    }
}
