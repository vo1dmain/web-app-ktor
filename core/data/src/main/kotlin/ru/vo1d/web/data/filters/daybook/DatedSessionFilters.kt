package ru.vo1d.web.data.filters.daybook

import kotlinx.datetime.LocalDateTime

data class DatedSessionFilters(
    val timetableId: Int? = null,
    val subject: String? = null,
    val instructor: String? = null,
    val place: String? = null,
    val typeId: Int? = null,
    val dateTime: LocalDateTime? = null
) {
    companion object {
        val Empty = DatedSessionFilters()
    }
}