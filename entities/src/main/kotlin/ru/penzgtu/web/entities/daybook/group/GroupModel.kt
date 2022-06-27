package ru.penzgtu.web.entities.daybook.group

import kotlinx.serialization.Serializable

@Serializable
data class GroupModel(
    val code: String,
    val levelId: String,
    val formId: String,
    val tableTypes: List<String>
)
