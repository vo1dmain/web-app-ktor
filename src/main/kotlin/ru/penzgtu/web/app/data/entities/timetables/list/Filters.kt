package ru.penzgtu.web.app.data.entities.timetables.list

import ru.penzgtu.web.app.data.dao.Filters
import ru.penzgtu.web.app.data.dao.FiltersBuilder

fun timetableFilters(block: Builder.() -> Unit): TimetableFilters {
    return Builder().apply(block).build()
}

interface TimetableFilters : Filters {
    val groupCode: String?
    val typeId: Int?

    override fun areEmpty(): Boolean {
        return groupCode == null || typeId == null
    }
}

class Builder: FiltersBuilder<TimetableFilters> {
    private var groupCode: String? = null
    private var typeId: Int? = null

    fun groupCode(value: String?) {
        groupCode = value
    }

    fun typeId(value: Int?) {
        typeId = value
    }

    override fun build(): TimetableFilters {
        return object : TimetableFilters {
            override val groupCode = this@Builder.groupCode
            override val typeId = this@Builder.typeId
        }
    }
}