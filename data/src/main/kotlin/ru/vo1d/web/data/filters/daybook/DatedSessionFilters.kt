package ru.vo1d.web.data.filters.daybook

import kotlinx.datetime.LocalDateTime
import ru.vo1d.web.data.filters.Filters

class DatedSessionFilters private constructor(builder: Builder) :
    Filters<DatedSessionFilters>(DatedSessionFilters::class) {
    val timetableId = builder.timetableId
    val subject = builder.subject
    val instructor = builder.instructor
    val place = builder.place
    val typeId = builder.typeId
    val datetime = builder.datetime

    class Builder : Filters.Builder<DatedSessionFilters> {
        var timetableId: Int? = null
        var subject: String? = null
        var instructor: String? = null
        var place: String? = null
        var typeId: Int? = null
        var datetime: LocalDateTime? = null

        override fun build() = DatedSessionFilters(this)
    }
}