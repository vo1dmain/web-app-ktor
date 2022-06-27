package ru.penzgtu.web.entities.daybook

import kotlinx.serialization.Serializable
import ru.penzgtu.web.entities.daybook.group.GroupModel
import ru.penzgtu.web.entities.daybook.group.form.EducationFormModel
import ru.penzgtu.web.entities.daybook.group.level.GraduationLevelModel
import ru.penzgtu.web.entities.daybook.group.type.TableTypeModel
import ru.penzgtu.web.entities.daybook.timetable.period.TimePeriodModel
import ru.penzgtu.web.entities.daybook.timetable.session.SessionTypeModel
import ru.penzgtu.web.entities.daybook.timetable.week.WeekOptionModel

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