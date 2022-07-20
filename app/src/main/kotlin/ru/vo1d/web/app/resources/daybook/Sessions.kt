package ru.vo1d.web.app.resources.daybook

import io.ktor.resources.*
import kotlinx.serialization.Serializable
import ru.vo1d.web.app.extensions.failIfNegative
import ru.vo1d.web.app.resources.ListResource

@Serializable
@Resource("/sessions")
data class Sessions(
    override val page: Int? = null,
    val timetable: Int? = null,
    val type: Int? = null,
    val day: Int? = null,
    val time: Int? = null,
    val weekOption: Int? = null
): ListResource {
    init {
        page?.failIfNegative()
        timetable?.failIfNegative()
        type?.failIfNegative()
        day?.failIfNegative()
        time?.failIfNegative()
        weekOption?.failIfNegative()
    }
}
