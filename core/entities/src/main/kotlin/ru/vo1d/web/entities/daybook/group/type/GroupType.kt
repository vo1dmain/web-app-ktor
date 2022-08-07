package ru.vo1d.web.entities.daybook.group.type

import kotlinx.serialization.Serializable

@Serializable
data class GroupType(
    val groupCode: String,
    val typeId: String
)
