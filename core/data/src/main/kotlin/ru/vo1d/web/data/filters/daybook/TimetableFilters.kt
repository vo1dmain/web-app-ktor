package ru.vo1d.web.data.filters.daybook

import ru.vo1d.web.entities.daybook.timetable.TimetableFormat

data class TimetableFilters(
    val groupCode: String? = null,
    val typeId: String? = null,
    val format: TimetableFormat? = null
) {
    companion object {
        val Empty = TimetableFilters()
    }
}

