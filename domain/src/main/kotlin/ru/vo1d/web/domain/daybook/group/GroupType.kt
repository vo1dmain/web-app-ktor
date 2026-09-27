package ru.vo1d.web.domain.daybook.group

import kotlinx.serialization.Serializable

@Serializable
data class GroupType(
    val groupCode: String,
    val typeId: String
)
