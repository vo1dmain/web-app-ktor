package ru.vo1d.web.entities.daybook.group

import kotlinx.serialization.Serializable

@Serializable
data class GroupDto(
    val code: String,
    val levelId: String,
    val degreeId: String?,
    val formId: String,
    val tableTypes: List<String>
)
