package ru.vo1d.web.entities.daybook

import kotlinx.serialization.Serializable
import ru.vo1d.web.entities.daybook.group.*
import ru.vo1d.web.entities.daybook.timetable.session.SessionType
import ru.vo1d.web.entities.daybook.timetable.week.Week

@Serializable
data class Meta(
    val week: Week,
    val levels: List<GraduationLevel>,
    val degrees: List<GraduationDegree>,
    val forms: List<EducationForm>,
    val tableTypes: List<TableType>,
    val groups: List<Group>,
    val sessionTypes: List<SessionType>
)