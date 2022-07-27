package ru.vo1d.web.app.resources.daybook

import io.ktor.resources.*
import kotlinx.serialization.Serializable
import ru.vo1d.web.app.extensions.failIfNegative
import ru.vo1d.web.app.resources.ListResource
import ru.vo1d.web.entities.daybook.timetable.TimetableFormat

@Serializable
@Resource("/timetables")
data class Timetables(
    override val page: Int? = null,
    val group: String? = null,
    val type: String? = null,
    val format: TimetableFormat? = null
) : ListResource {
    init {
        page?.failIfNegative()
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
