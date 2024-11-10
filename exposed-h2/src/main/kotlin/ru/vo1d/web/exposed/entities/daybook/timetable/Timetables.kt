package ru.vo1d.web.exposed.entities.daybook.timetable

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.InnerTableLink
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption.CASCADE
import ru.vo1d.web.entities.daybook.timetable.TimetableFormat
import ru.vo1d.web.exposed.entities.daybook.group.Groups
import ru.vo1d.web.exposed.entities.daybook.group.TableTypes

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

internal class TimetableWithSessionsEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<TimetableWithSessionsEntity>(Timetables)

    val groupCode by Timetables.groupCode
    val typeId by Timetables.typeId
    val format by Timetables.format
    val sessions by loadSessions(format)

    private fun loadSessions(format: TimetableFormat): InnerTableLink<Int, Entity<Int>, Int, SessionEntity<*>> {
        return when (format) {
            TimetableFormat.Regular -> RegularSessionEntity via TimetableRegularSessions
            TimetableFormat.Dated -> DatedSessionEntity via TimetableDatedSessions
        }
    }
}
