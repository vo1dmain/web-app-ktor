package ru.vo1d.web.exposed.entities.daybook.timetable

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption.CASCADE
import org.jetbrains.exposed.sql.SizedIterable
import ru.vo1d.web.entities.daybook.timetable.Timetable
import ru.vo1d.web.entities.daybook.timetable.TimetableFormat
import ru.vo1d.web.entities.daybook.timetable.TimetableWithSessions
import ru.vo1d.web.exposed.entities.HasModel
import ru.vo1d.web.exposed.entities.daybook.group.Groups
import ru.vo1d.web.exposed.entities.daybook.group.TableTypes

object Timetables : IntIdTable() {
    val groupCode = reference("groupCode", Groups, CASCADE, CASCADE)
    val typeId = reference("typeId", TableTypes, CASCADE, CASCADE)
    val format = enumerationByName("format", 10, TimetableFormat::class)

    init {
        uniqueIndex(groupCode, typeId, format)
    }
}

class TimetableEntity(id: EntityID<Int>) : IntEntity(id), HasModel<Timetable> {
    companion object : IntEntityClass<TimetableEntity>(Timetables)

    val groupCode by Timetables.groupCode
    val typeId by Timetables.typeId
    val format by Timetables.format

    override fun toModel() =
        Timetable(id.value, groupCode.value, typeId.value, format)
}

class TimetableWithSessionsEntity(id: EntityID<Int>) : IntEntity(id), HasModel<TimetableWithSessions<*>> {
    companion object : IntEntityClass<TimetableWithSessionsEntity>(Timetables)

    val groupCode by Timetables.groupCode
    val typeId by Timetables.typeId
    val format by Timetables.format
    val sessions by lazy { loadSessions(format) }

    private fun loadSessions(format: TimetableFormat): SizedIterable<SessionEntity<*>> {
        return when (format) {
            TimetableFormat.Regular -> RegularSessionEntity via TimetableRegularSessions
            TimetableFormat.Dated -> DatedSessionEntity via TimetableDatedSessions
        }.getValue(this, this::sessions)
    }

    override fun toModel() = TimetableWithSessions(
        id.value,
        groupCode.value,
        typeId.value,
        format,
        sessions.map { it.toModel() }
    )
}
