package ru.vo1d.web.app.resources.daybook

import io.ktor.resources.*
import kotlinx.serialization.Serializable

@Serializable
@Resource("/meta")
class Meta {
    @Serializable
    @Resource("/levels")
    data class Levels(val parent: Meta = Meta())

    @Serializable
    @Resource("/degrees")
    data class Degrees(val parent: Meta = Meta())

    @Serializable
    @Resource("/forms")
    data class Forms(val parent: Meta = Meta())

    @Serializable
    @Resource("/table-types")
    data class TableTypes(val parent: Meta = Meta())

    @Serializable
    @Resource("/groups")
    data class Groups(val parent: Meta = Meta())

    @Serializable
    @Resource("/start-times")
    data class Times(val parent: Meta = Meta())

    @Serializable
    @Resource("/session-types")
    data class SessionTypes(val parent: Meta = Meta())

    @Serializable
    @Resource("/week-options")
    data class WeekOptions(val parent: Meta = Meta())
}
