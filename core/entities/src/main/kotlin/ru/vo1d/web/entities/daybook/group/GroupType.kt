package ru.vo1d.web.entities.daybook.group

import kotlinx.serialization.Serializable

@Serializable
data class GroupType(
    val groupCode: String,
    val typeId: String
)
