package ru.penzgtu.web.app.data.timetables.meta

import kotlinx.serialization.Serializable
import ru.penzgtu.web.app.data.timetables.meta.parts.*

@Serializable
data class Meta(
    val levels: List<GraduationLevel>,
    val forms: List<EducationForm>,
    val tableTypes: List<TableType>,
    val groups: List<Group>,
    val periods: List<TimePeriod>,
    val sessionTypes: List<SessionType>,
    val weekOptions: List<WeekOption>
)