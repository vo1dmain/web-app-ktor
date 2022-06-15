package ru.penzgtu.web.app.data.timetables.meta

import kotlinx.serialization.Serializable

@Serializable
data class MetaRoot(
    val graduationLevels: List<GraduationLevel>
)