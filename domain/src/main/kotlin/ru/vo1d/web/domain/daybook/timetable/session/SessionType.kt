package ru.vo1d.web.domain.daybook.timetable.session

import kotlinx.serialization.Serializable

@Serializable
data class SessionType(
    val id: Int? = null,
    val title: String
)