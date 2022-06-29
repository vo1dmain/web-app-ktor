package ru.vo1d.web.app.exposed.orm.daybook.timetable

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption.CASCADE
import ru.vo1d.web.app.exposed.orm.HasDto
import ru.vo1d.web.app.exposed.orm.HasModel
import ru.vo1d.web.app.exposed.orm.daybook.group.Groups
import ru.vo1d.web.app.exposed.orm.daybook.group.TableTypes
import ru.vo1d.web.entities.daybook.timetable.TimetableDto
import ru.vo1d.web.entities.daybook.timetable.TimetableModel

object Timetables : IntIdTable() {
    val groupCode = reference("groupCode", Groups, CASCADE, CASCADE)
    val typeId = reference("typeId", TableTypes, CASCADE, CASCADE)
}

open class Timetable(id: EntityID<Int>) : IntEntity(id), HasModel<TimetableModel> {
    companion object : IntEntityClass<Timetable>(Timetables)

    val groupCode by Timetables.groupCode
    val typeId by Timetables.typeId

    override fun toModel() = TimetableModel(id.value, groupCode.value, typeId.value)
}

class TimetableWithData(id: EntityID<Int>) : Timetable(id), HasDto<TimetableDto> {
    companion object : IntEntityClass<TimetableWithData>(Timetables)

    val sessions by Session via TimetableSessions
    val days by Day via Sessions

    override fun toDto() = TimetableDto(
        id.value,
        groupCode.value,
        typeId.value,
        days.map(Day::toModel),
        sessions.map(Session::toModel)
    )
}