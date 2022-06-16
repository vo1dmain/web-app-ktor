package ru.penzgtu.web.app.data.entities.timetables.list.parts

import kotlinx.serialization.Serializable

@Serializable
data class Day(
    val id: Int?,
    val title: String
)
