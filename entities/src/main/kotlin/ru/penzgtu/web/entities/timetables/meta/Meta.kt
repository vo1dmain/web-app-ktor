package ru.penzgtu.web.entities.timetables.meta

import kotlinx.serialization.Serializable
import ru.penzgtu.web.entities.timetables.meta.parts.*

@Serializable
data class Meta(
    val levels: List<GraduationLevel>,
    val forms: List<EducationForm>,
    val tableTypes: List<TableTypeModel>,
    val groups: List<GroupModel>,
    val periods: List<TimePeriodModel>,
    val sessionTypes: List<SessionTypeModel>,
    val weekOptions: List<WeekOptionModel>
)