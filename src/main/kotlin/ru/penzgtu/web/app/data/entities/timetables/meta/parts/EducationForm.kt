package ru.penzgtu.web.app.data.entities.timetables.meta.parts

import kotlinx.serialization.Serializable

@Serializable
data class EducationForm(
    val id: Int?,
    val title: String
)
