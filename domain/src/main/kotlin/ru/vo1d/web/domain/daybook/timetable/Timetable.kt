package ru.vo1d.web.domain.daybook.timetable

data class Timetable(
    val id: Int,
    val groupCode: String,
    val typeId: String,
    val format: TimetableFormat
)

data class NewTimetable(
    val groupCode: String,
    val typeId: String,
    val format: TimetableFormat
)
