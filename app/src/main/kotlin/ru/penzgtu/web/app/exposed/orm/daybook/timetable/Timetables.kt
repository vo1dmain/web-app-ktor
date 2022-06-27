package ru.penzgtu.web.app.exposed.orm.daybook.timetable

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption.CASCADE
import ru.penzgtu.web.app.exposed.orm.HasModel
import ru.penzgtu.web.app.exposed.orm.HasView
import ru.penzgtu.web.app.exposed.orm.daybook.group.Groups
import ru.penzgtu.web.app.exposed.orm.daybook.group.TableTypes
import ru.penzgtu.web.entities.daybook.timetable.TimetableModel
import ru.penzgtu.web.entities.daybook.timetable.TimetableView

object Timetables : IntIdTable() {
    val groupCode = reference("groupCode", Groups, CASCADE, CASCADE)
    val typeId = reference("typeId", TableTypes, CASCADE, CASCADE)
}

class Timetable(id: EntityID<Int>) : IntEntity(id), HasModel<TimetableModel>, HasView<TimetableView> {
    companion object : IntEntityClass<Timetable>(Timetables)

    val groupCode by Timetables.groupCode
    val typeId by Timetables.typeId
    val sessions by Session via TimetableSessions
    val days by Day referrersOn Sessions.dayId

    override fun toModel() = TimetableModel(
        id.value,
        groupCode.value,
        typeId.value,
        days.map(Day::toModel),
        sessions.map(Session::toModel),
    )

    override fun toView() = TimetableView(id.value, groupCode.value, typeId.value)
}