package ru.vo1d.web.entities.daybook

import kotlinx.serialization.Serializable
import ru.vo1d.web.entities.daybook.group.GroupDto
import ru.vo1d.web.entities.daybook.group.degree.GradDegreeModel
import ru.vo1d.web.entities.daybook.group.form.EduFormModel
import ru.vo1d.web.entities.daybook.group.level.GradLevelModel
import ru.vo1d.web.entities.daybook.group.type.TableTypeModel
import ru.vo1d.web.entities.daybook.timetable.session.SessionTypeModel
import ru.vo1d.web.entities.daybook.timetable.time.StartTimeModel
import ru.vo1d.web.entities.daybook.timetable.week.WeekModel

@Serializable
data class Meta(
    val week: WeekModel,
    val levels: List<GradLevelModel>,
    val degrees: List<GradDegreeModel>,
    val forms: List<EduFormModel>,
    val tableTypes: List<TableTypeModel>,
    val groups: List<GroupDto>,
    val startTimes: List<StartTimeModel>,
    val sessionTypes: List<SessionTypeModel>
)