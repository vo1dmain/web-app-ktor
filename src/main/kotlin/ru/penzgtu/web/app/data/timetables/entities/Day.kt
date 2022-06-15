package ru.penzgtu.web.app.data.timetables.entities

import kotlinx.serialization.Serializable

@Serializable
data class Day(
    val id: Int,
    val title: String
)
