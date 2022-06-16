package ru.penzgtu.web.app.data.entities.timetables.meta

import kotlinx.serialization.Serializable

@Serializable
data class Week(
    val number: Int,
    val title: String
)
