package ru.vo1d.web.domain.daybook

import ru.vo1d.web.domain.daybook.timetable.TimetableFormat

data class TimetableFilters(
    val groupCode: String? = null,
    val typeId: String? = null,
    val format: TimetableFormat? = null
)
