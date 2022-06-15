package ru.penzgtu.web.app.data.timetables.meta

import kotlinx.serialization.Serializable

@Serializable
data class Meta(
    val levels: List<GraduationLevel>,
    val forms: List<EducationForm>,
    val types: List<TableType>,
    val groups: List<Group>
)