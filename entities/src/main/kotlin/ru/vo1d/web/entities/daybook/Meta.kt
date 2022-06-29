package ru.vo1d.web.entities.daybook

import kotlinx.serialization.Serializable
import ru.vo1d.web.entities.daybook.group.GroupModel
import ru.vo1d.web.entities.daybook.group.degree.GradDegreeModel
import ru.vo1d.web.entities.daybook.group.form.EduFormModel
import ru.vo1d.web.entities.daybook.group.level.GradLevelModel
import ru.vo1d.web.entities.daybook.group.type.TableTypeModel
import ru.vo1d.web.entities.daybook.timetable.period.TimePeriodModel
import ru.vo1d.web.entities.daybook.timetable.session.SessionTypeModel
import ru.vo1d.web.entities.daybook.timetable.week.WeekOptionModel

@Serializable
data class Meta(
    val levels: List<GradLevelModel>,
    val degrees: List<GradDegreeModel>,
    val forms: List<EduFormModel>,
    val tableTypes: List<TableTypeModel>,
    val groups: List<GroupModel>,
    val periods: List<TimePeriodModel>,
    val sessionTypes: List<SessionTypeModel>,
    val weekOptions: List<WeekOptionModel>
)