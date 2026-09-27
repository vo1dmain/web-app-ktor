package ru.vo1d.web.persistence.daybook

import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.core.like
import org.jetbrains.exposed.v1.jdbc.andWhere
import org.jetbrains.exposed.v1.jdbc.insertIgnoreAndGetId
import org.jetbrains.exposed.v1.jdbc.selectAll
import ru.vo1d.web.domain.PageRequest
import ru.vo1d.web.domain.daybook.TimetableFilters
import ru.vo1d.web.domain.daybook.TimetableRepository
import ru.vo1d.web.domain.daybook.timetable.NewTimetable
import ru.vo1d.web.domain.daybook.timetable.Timetable
import ru.vo1d.web.domain.errors.AlreadyExistsException
import ru.vo1d.web.persistence.XpRepository
import ru.vo1d.web.persistence.context.DbContext

class TimetableRepositoryXp(ctx: DbContext) : XpRepository(ctx.daybook), TimetableRepository {
    override suspend fun find(filters: TimetableFilters, page: PageRequest): List<Timetable> = query {
        val query = Timetables.selectAll().apply {
            filters.typeId?.let { andWhere { Timetables.typeId eq it } }
            filters.groupCode?.let { andWhere { Timetables.groupCode like it } }
            filters.format?.let { andWhere { Timetables.format eq it } }
            orderBy(Timetables.id)
            limit(page.size)
            offset(page.offset)
        }

        TimetableEntity.wrapRows(query).map(TimetableEntity::toDomain)
    }

    override suspend fun get(id: Int): Timetable? = query {
        TimetableEntity.findById(id)?.toDomain()
    }

    override suspend fun add(timetable: NewTimetable): Int = query {
        Timetables.insertIgnoreAndGetId { it.mapItem(timetable) }?.value
            ?: throw AlreadyExistsException("The group already has such a timetable")
    }
}
