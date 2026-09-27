package ru.vo1d.web.domain.filters.daybook

import kotlin.time.Instant

data class DatedSessionFilters(
    val timetableId: Int? = null,
    val subject: String? = null,
    val instructor: String? = null,
    val place: String? = null,
    val typeId: Int? = null,
    val dateTime: Instant? = null
) {
    companion object {
        val Empty = DatedSessionFilters()
    }
}