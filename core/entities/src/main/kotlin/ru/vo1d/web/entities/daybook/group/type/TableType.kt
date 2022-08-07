package ru.vo1d.web.entities.daybook.group.type

import kotlinx.serialization.Serializable

@Serializable
data class TableType(
    val id: String,
    val title: String
)
