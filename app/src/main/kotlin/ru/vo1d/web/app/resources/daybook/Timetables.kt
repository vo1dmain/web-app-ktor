package ru.vo1d.web.app.resources.daybook

import io.ktor.resources.*
import kotlinx.serialization.Serializable
import ru.vo1d.web.app.extensions.failIfNegative

@Serializable
@Resource("/timetables")
data class Timetables(
    val page: Int? = null,
    val group: String? = null,
    val type: String? = null
) {
    init {
        page?.failIfNegative()
    }

    @Serializable
    @Resource("{id}")
    data class Id(val parent: Timetables = Timetables(), val id: Int) {
        init {
            id.failIfNegative()
        }

        @Serializable
        @Resource("/sessions")
        data class Sessions(
            val parent: Id,
            val page: Int? = null,
            val type: Int? = null,
            val day: Int? = null,
            val time: Int? = null,
            val weekOption: Int? = null
        ) {
            init {
                page?.failIfNegative()
                type?.failIfNegative()
                day?.failIfNegative()
                time?.failIfNegative()
                weekOption?.failIfNegative()
            }
        }
    }
}
