package ru.vo1d.web.orm.entities.daybook.timetable

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption.CASCADE
import org.jetbrains.exposed.sql.SizedIterable
import ru.vo1d.web.entities.daybook.timetable.TimetableDto
import ru.vo1d.web.entities.daybook.timetable.TimetableFormat
import ru.vo1d.web.entities.daybook.timetable.TimetableModel
import ru.vo1d.web.orm.entities.HasDto
import ru.vo1d.web.orm.entities.HasModel
import ru.vo1d.web.orm.entities.daybook.group.Groups
import ru.vo1d.web.orm.entities.daybook.group.TableTypes

object Timetables : IntIdTable() {
    val groupCode = reference("groupCode", Groups, CASCADE, CASCADE)
    val typeId = reference("typeId", TableTypes, CASCADE, CASCADE)
    val format = enumerationByName("format", 10, TimetableFormat::class)
    val index = uniqueIndex(groupCode, typeId, format)
}

open class Timetable(id: EntityID<Int>) : IntEntity(id), HasModel<TimetableModel> {
    companion object : IntEntityClass<Timetable>(Timetables)

    val groupCode by Timetables.groupCode
    val typeId by Timetables.typeId
    val format by Timetables.format

    override fun toModel() = TimetableModel(id.value, groupCode.value, typeId.value, format)
}

class TimetableWithSessions(id: EntityID<Int>) : Timetable(id), HasDto<TimetableDto<*>> {
    companion object : IntEntityClass<TimetableWithSessions>(Timetables)

    val sessions by lazy { loadSessions(format) }

    private fun loadSessions(format: TimetableFormat): SizedIterable<SessionEntity<*>> {
        return when (format) {
            TimetableFormat.Regular -> RegularSession via TimetableRegularSessions
            TimetableFormat.Dated -> DatedSession via TimetableDatedSessions
        }.getValue(this, this::sessions)
    }

    override fun toDto() = TimetableDto(
        id.value,
        groupCode.value,
        typeId.value,
        format,
        sessions.map { it.toModel() }
    )
}
