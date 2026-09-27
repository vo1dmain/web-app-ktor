package ru.vo1d.web.domain.daybook

import ru.vo1d.web.domain.daybook.group.*
import ru.vo1d.web.domain.daybook.timetable.session.SessionType

/**
 * Daybook lookup data: small, rarely changing lists shown to clients as-is.
 */
interface ReferenceRepository {
    suspend fun levels(): List<GraduationLevel>

    suspend fun degrees(): List<GraduationDegree>

    suspend fun forms(): List<EducationForm>

    suspend fun tableTypes(): List<TableType>

    suspend fun sessionTypes(): List<SessionType>

    suspend fun groups(): List<Group>
}
