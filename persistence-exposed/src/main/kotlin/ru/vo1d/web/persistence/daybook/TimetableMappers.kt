package ru.vo1d.web.persistence.daybook

import org.jetbrains.exposed.v1.core.statements.UpdateBuilder
import ru.vo1d.web.domain.daybook.timetable.NewTimetable
import ru.vo1d.web.domain.daybook.timetable.Timetable

internal fun UpdateBuilder<*>.mapItem(item: NewTimetable) {
    this[Timetables.groupCode] = item.groupCode
    this[Timetables.typeId] = item.typeId
    this[Timetables.format] = item.format
}

internal fun TimetableEntity.toDomain() = Timetable(id.value, groupCode.value, typeId.value, format)
