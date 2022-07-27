package ru.vo1d.web.entities.daybook.timetable

import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import ru.vo1d.web.entities.daybook.timetable.session.DatedSessionModel
import ru.vo1d.web.entities.daybook.timetable.session.RegularSessionModel
import ru.vo1d.web.entities.daybook.timetable.session.SessionModel

@Serializable
data class TimetableDto<out T : SessionModel>(
    val id: Int,
    val groupCode: String,
    val typeId: String,
    val format: TimetableFormat,
    val sessions: List<T>,
)

val dtoModule = SerializersModule {
    polymorphic(SessionModel::class) {
        subclass(RegularSessionModel::class)
        subclass(DatedSessionModel::class)
    }
}
