package ru.penzgtu.web.app.exposed.orm.daybook.timetable

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import ru.penzgtu.web.app.exposed.orm.HasModel
import ru.penzgtu.web.entities.daybook.timetable.period.TimePeriodModel

object TimePeriods : IntIdTable() {
    val start = varchar("start", 8)
    val end = varchar("end", 8)
}

class TimePeriod(id: EntityID<Int>) : IntEntity(id), HasModel<TimePeriodModel> {
    companion object : IntEntityClass<TimePeriod>(TimePeriods)

    val start by TimePeriods.start
    val end by TimePeriods.end

    override fun toModel() = TimePeriodModel(id.value, start, end)
}