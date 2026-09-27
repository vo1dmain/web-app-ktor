package ru.vo1d.web.domain.daybook.group

import kotlinx.serialization.Serializable

@Serializable
data class GraduationLevel(
    val id: String,
    val title: String
)