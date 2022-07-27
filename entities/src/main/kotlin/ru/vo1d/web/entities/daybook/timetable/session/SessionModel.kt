package ru.vo1d.web.entities.daybook.timetable.session

import kotlinx.datetime.DateTimePeriod

sealed interface SessionModel {
    val subject: String
    val instructor: String
    val place: String
    val typeId: Int
    val duration: DateTimePeriod?
}