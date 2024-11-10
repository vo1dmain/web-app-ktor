package ru.vo1d.web.exposed.mappers

import org.jetbrains.exposed.sql.statements.UpdateBuilder
import ru.vo1d.web.entities.daybook.timetable.Timetable
import ru.vo1d.web.entities.daybook.timetable.TimetableWithSessions
import ru.vo1d.web.exposed.entities.daybook.timetable.*

internal fun UpdateBuilder<*>.mapItem(item: Timetable) {
    this[Timetables.groupCode] = item.groupCode
    this[Timetables.typeId] = item.typeId
    this[Timetables.format] = item.format
}


internal fun TimetableEntity.toDomain() = Timetable(id.value, groupCode.value, typeId.value, format)

internal fun TimetableWithSessionsEntity.toDomain() = TimetableWithSessions(
    id.value,
    groupCode.value,
    typeId.value,
    format,
    sessions.mapNotNull {
        when (it) {
            is DatedSessionEntity -> it.toDomain()
            is RegularSessionEntity -> it.toDomain()
            else -> null
        }
    }
)