package ru.vo1d.web.domain.daybook.timetable.session

import kotlin.time.Duration

sealed interface Session {
    val id: Int
    val subject: String
    val instructor: String
    val place: String
    val typeId: Int
    val duration: Duration
}
