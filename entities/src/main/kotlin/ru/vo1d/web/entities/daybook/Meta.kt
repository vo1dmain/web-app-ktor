package ru.vo1d.web.entities.daybook

import kotlinx.serialization.Serializable
import ru.vo1d.web.entities.daybook.group.GroupModel
import ru.vo1d.web.entities.daybook.group.form.EducationFormModel
import ru.vo1d.web.entities.daybook.group.level.GraduationLevelModel
import ru.vo1d.web.entities.daybook.group.type.TableTypeModel
import ru.vo1d.web.entities.daybook.timetable.period.TimePeriodModel
import ru.vo1d.web.entities.daybook.timetable.session.SessionTypeModel
import ru.vo1d.web.entities.daybook.timetable.week.WeekOptionModel

@Serializable
data class Meta(
    val levels: List<GraduationLevelModel>,
    val forms: List<EducationFormModel>,
    val tableTypes: List<TableTypeModel>,
    val groups: List<GroupModel>,
    val periods: List<TimePeriodModel>,
    val sessionTypes: List<SessionTypeModel>,
    val weekOptions: List<WeekOptionModel>
)