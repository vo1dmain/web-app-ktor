package ru.vo1d.web.entities.daybook

import kotlinx.serialization.Serializable
import ru.vo1d.web.entities.daybook.group.Group
import ru.vo1d.web.entities.daybook.group.degree.GraduationDegree
import ru.vo1d.web.entities.daybook.group.form.EducationForm
import ru.vo1d.web.entities.daybook.group.level.GraduationLevel
import ru.vo1d.web.entities.daybook.group.type.TableType
import ru.vo1d.web.entities.daybook.timetable.session.SessionType
import ru.vo1d.web.entities.daybook.timetable.time.StartTime
import ru.vo1d.web.entities.daybook.timetable.week.Week

@Serializable
data class Meta(
    val week: Week,
    val levels: List<GraduationLevel>,
    val degrees: List<GraduationDegree>,
    val forms: List<EducationForm>,
    val tableTypes: List<TableType>,
    val groups: List<Group>,
    val startTimes: List<StartTime>,
    val sessionTypes: List<SessionType>
)