package ru.vo1d.web.data.filters.daybook

import ru.vo1d.web.data.filters.Filters

class TimetableFilters private constructor(builder: Builder) : Filters<TimetableFilters>(TimetableFilters::class) {
    val groupCode = builder.groupCode
    val typeId = builder.typeId

    class Builder : Filters.Builder<TimetableFilters> {
        var groupCode: String? = null
        var typeId: String? = null

        override fun build() = TimetableFilters(this)
    }
}

