package ru.penzgtu.web.entities.timetables.meta.parts

import kotlinx.serialization.Serializable

@Serializable
data class EducationForm(
    val id: Int?,
    val title: String
)
