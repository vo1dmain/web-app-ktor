package ru.vo1d.web.entities.daybook.group.level

import kotlinx.serialization.Serializable

@Serializable
data class GraduationLevelModel(
    val id: String,
    val title: String
)