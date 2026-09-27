package ru.vo1d.web.api.resources.daybook

import io.ktor.resources.*
import kotlinx.serialization.Serializable
import ru.vo1d.web.api.extensions.failIfNegative
import ru.vo1d.web.api.extensions.failIfNotPositive
import ru.vo1d.web.api.resources.ListResource
import ru.vo1d.web.domain.daybook.timetable.TimetableFormat

@Serializable
@Resource("/timetables")
data class Timetables(
    override val page: Int? = null,
    val group: String? = null,
    val type: String? = null,
    val format: TimetableFormat? = null
) : ListResource {
    init {
        page?.failIfNotPositive()
    }

    @Serializable
    @Resource("/timetables/{id}")
    data class Id(val id: Int) {
        init {
            id.failIfNegative()
        }

        @Serializable
        @Resource("/sessions")
        data class Sessions(val parent: Id)
    }
}
