package ru.vo1d.web.entities.daybook.timetable.session

import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import kotlin.time.Duration

sealed interface Session {
    val subject: String
    val instructor: String
    val place: String
    val typeId: Int
    val duration: Duration?
}

val sessionsModule = SerializersModule {
    polymorphic(Session::class) {
        subclass(RegularSession::class)
        subclass(DatedSession::class)
    }
}