package ru.vo1d.web.entities.daybook.timetable.week

import kotlinx.serialization.Serializable

@Serializable
data class WeekModel(val number: Int, val title: String) {
    companion object {
        val FIRST = WeekModel(1, "Первая неделя")
        val SECOND = WeekModel(2, "Вторая неделя")
    }
}
