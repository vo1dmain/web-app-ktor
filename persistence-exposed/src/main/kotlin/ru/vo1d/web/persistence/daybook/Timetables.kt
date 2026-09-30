package ru.vo1d.web.persistence.daybook

import org.jetbrains.exposed.v1.core.ReferenceOption.CASCADE
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass
import ru.vo1d.web.domain.daybook.timetable.TimetableFormat

internal object Timetables : IntIdTable() {
    val groupCode = reference("groupCode", Groups, CASCADE, CASCADE)
    val typeId = reference("typeId", TableTypes, CASCADE, CASCADE)
    val format = enumerationByName("format", 10, TimetableFormat::class)

    init {
        uniqueIndex(groupCode, typeId, format)
    }
}

internal class TimetableEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<TimetableEntity>(Timetables)

    val groupCode by Timetables.groupCode
    val typeId by Timetables.typeId
    val format by Timetables.format
}
