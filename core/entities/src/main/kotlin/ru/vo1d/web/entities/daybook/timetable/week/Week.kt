package ru.vo1d.web.entities.daybook.timetable.week

import kotlinx.serialization.Serializable

@Serializable
data class Week(
    val number: Int,
    val title: String
) {
    companion object {
        val FIRST = Week(1, "Первая неделя")
        val SECOND = Week(2, "Вторая неделя")
    }
}
