package ru.vo1d.web.api.dto.daybook

import kotlinx.serialization.Serializable
import ru.vo1d.web.domain.daybook.Meta
import ru.vo1d.web.domain.daybook.group.*
import ru.vo1d.web.domain.daybook.timetable.session.SessionType
import ru.vo1d.web.domain.daybook.timetable.week.Week

@Serializable
data class WeekResponse(
    val number: Int,
    val title: String
)

/**
 * A lookup item identified by a string code: graduation level, degree, education form or table type.
 */
@Serializable
data class ReferenceResponse(
    val id: String,
    val title: String
)

@Serializable
data class SessionTypeResponse(
    val id: Int,
    val title: String
)

@Serializable
data class GroupResponse(
    val code: String,
    val levelId: String,
    val degreeId: String?,
    val formId: String,
    val tableTypes: List<String>? = null
)

@Serializable
data class MetaResponse(
    val week: WeekResponse,
    val levels: List<ReferenceResponse>,
    val degrees: List<ReferenceResponse>,
    val forms: List<ReferenceResponse>,
    val tableTypes: List<ReferenceResponse>,
    val groups: List<GroupResponse>,
    val timeZone: String,
    val sessionTypes: List<SessionTypeResponse>
)

fun Week.toResponse() = WeekResponse(number, title)

fun GraduationLevel.toResponse() = ReferenceResponse(id, title)

fun GraduationDegree.toResponse() = ReferenceResponse(id, title)

fun EducationForm.toResponse() = ReferenceResponse(id, title)

fun TableType.toResponse() = ReferenceResponse(id, title)

fun SessionType.toResponse() = SessionTypeResponse(id, title)

fun Group.toResponse() = GroupResponse(code, levelId, degreeId, formId, tableTypes)

fun Meta.toResponse() = MetaResponse(
    week.toResponse(),
    levels.map { it.toResponse() },
    degrees.map { it.toResponse() },
    forms.map { it.toResponse() },
    tableTypes.map { it.toResponse() },
    groups.map { it.toResponse() },
    timeZone,
    sessionTypes.map { it.toResponse() }
)
