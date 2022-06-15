package ru.penzgtu.web.app.data.timetables.meta.parts

import kotlinx.serialization.Serializable

@Serializable
data class Group(
    val code: String,
    val levelId: Int,
    val formId: Int,
    val tableTypes: List<Int>
)
