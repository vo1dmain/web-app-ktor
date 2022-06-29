package ru.vo1d.web.app.data.filters.daybook

import ru.vo1d.web.app.data.dao.filters.Filters
import ru.vo1d.web.app.data.dao.filters.FiltersBuilder
import ru.vo1d.web.app.data.dao.filters.FiltersCreator


interface SessionFilters : Filters {
    companion object : FiltersCreator<SessionFilters, Builder>(Builder::class)

    val timetableId: Int?
    val typeId: Int?
    val dayId: Int?
    val periodId: Int?
    val weekOptionId: Int?

    override fun areEmpty() = listOf(timetableId, typeId, dayId, periodId, weekOptionId).all { it == null }

    class Builder internal constructor() : FiltersBuilder<SessionFilters> {
        var timetableId: Int? = null
        var typeId: Int? = null
        var dayId: Int? = null
        var periodId: Int? = null
        var weekOptionId: Int? = null

        override fun build() = object : SessionFilters {
            override val timetableId = this@Builder.timetableId
            override val typeId = this@Builder.typeId
            override val dayId = this@Builder.dayId
            override val periodId = this@Builder.periodId
            override val weekOptionId = this@Builder.weekOptionId
        }
    }
}