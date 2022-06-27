package ru.penzgtu.web.entities.timetables.meta.parts

import kotlinx.serialization.Serializable

@Serializable
data class GroupModel(
    val code: String,
    val levelId: Int,
    val formId: Int,
    val tableTypes: List<Int>
)
