package ru.vo1d.web.domain.daybook

import kotlinx.serialization.Serializable
import ru.vo1d.web.domain.daybook.group.*
import ru.vo1d.web.domain.daybook.timetable.session.SessionType
import ru.vo1d.web.domain.daybook.timetable.week.Week

@Serializable
data class Meta(
    val week: Week,
    val levels: List<GraduationLevel>,
    val degrees: List<GraduationDegree>,
    val forms: List<EducationForm>,
    val tableTypes: List<TableType>,
    val groups: List<Group>,
    val timeZone: String,
    val sessionTypes: List<SessionType>
)