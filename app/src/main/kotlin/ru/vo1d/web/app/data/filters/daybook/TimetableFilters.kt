package ru.vo1d.web.app.data.filters.daybook

import ru.vo1d.web.app.data.dao.Filters
import ru.vo1d.web.app.data.dao.FiltersBuilder

fun timetableFilters(block: TimetableFilters.Companion.() -> Unit): TimetableFilters {
    return TimetableFilters.apply(block).build()
}

interface TimetableFilters : Filters {
    val groupCode: String?
    val typeId: String?

    override fun areEmpty(): Boolean {
        return groupCode == null || typeId == null
    }

    companion object : FiltersBuilder<TimetableFilters> {
        private var groupCode: String? = null
        private var typeId: String? = null

        fun groupCode(value: String?) {
            groupCode = value
        }

        fun typeId(value: String?) {
            typeId = value
        }

        override fun build(): TimetableFilters {
            return object : TimetableFilters {
                override val groupCode = this@Companion.groupCode
                override val typeId = this@Companion.typeId
            }
        }
    }
}

