package ru.vo1d.web.persistence.daybook

import ru.vo1d.web.domain.daybook.ReferenceRepository
import ru.vo1d.web.domain.daybook.group.*
import ru.vo1d.web.domain.daybook.timetable.session.SessionType
import ru.vo1d.web.persistence.XpRepository
import ru.vo1d.web.persistence.context.DbContext
import ru.vo1d.web.persistence.entities.daybook.group.*
import ru.vo1d.web.persistence.entities.daybook.timetable.SessionTypeEntity
import ru.vo1d.web.persistence.mappers.toDomain

class ReferenceRepositoryXp(ctx: DbContext) : XpRepository(ctx.daybook), ReferenceRepository {
    override suspend fun levels(): List<GraduationLevel> = query {
        GraduationLevelEntity.all().map(GraduationLevelEntity::toDomain)
    }

    override suspend fun degrees(): List<GraduationDegree> = query {
        GraduationDegreeEntity.all().map(GraduationDegreeEntity::toDomain)
    }

    override suspend fun forms(): List<EducationForm> = query {
        EducationFormEntity.all().map(EducationFormEntity::toDomain)
    }

    override suspend fun tableTypes(): List<TableType> = query {
        TableTypeEntity.all().map(TableTypeEntity::toDomain)
    }

    override suspend fun sessionTypes(): List<SessionType> = query {
        SessionTypeEntity.all()
            .sortedBy { it.id.value }
            .map(SessionTypeEntity::toDomain)
    }

    override suspend fun groups(): List<Group> = query {
        GroupEntity.all().map(GroupEntity::toDomain)
    }
}
