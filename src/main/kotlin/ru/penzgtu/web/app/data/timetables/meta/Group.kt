package ru.penzgtu.web.app.data.timetables.meta

import kotlinx.serialization.Serializable

@Serializable
data class Group(
    val id: Int,
    val code: String,
    val levelId: Int,
    val formId: Int,
    val tableTypes: List<Int>
)
