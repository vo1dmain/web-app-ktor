package ru.penzgtu.web.app.exposed.orm.timetables

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption.CASCADE
import ru.penzgtu.web.app.exposed.orm.HasModel
import ru.penzgtu.web.app.exposed.orm.HasView
import ru.penzgtu.web.entities.timetables.list.TimetableModel
import ru.penzgtu.web.entities.timetables.list.TimetableView

object Timetables : IntIdTable() {
    val groupCode = varchar("groupCode", 8)
    val typeId = reference("typeId", TableTypes, CASCADE, CASCADE)
}

class Timetable(id: EntityID<Int>) : IntEntity(id), HasModel<TimetableModel>, HasView<TimetableView> {
    companion object : IntEntityClass<Timetable>(Timetables)

    val groupCode by Timetables.groupCode
    val typeId by Timetables.typeId
    val sessions by Session via TimetableSessions
    val days by Day referrersOn Sessions.dayId

    override fun model() = TimetableModel(
        id.value,
        groupCode,
        typeId.value,
        days.map(Day::model),
        sessions.map(Session::model),
    )

    override fun toView() = TimetableView(id.value, groupCode, typeId.value)
}