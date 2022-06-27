package ru.vo1d.web.entities.daybook.group.type

import kotlinx.serialization.Serializable

@Serializable
data class TableTypeModel(
    val id: String,
    val title: String
)
