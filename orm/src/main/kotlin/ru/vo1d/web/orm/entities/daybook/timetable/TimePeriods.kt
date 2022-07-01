package ru.vo1d.web.orm.entities.daybook.timetable

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import ru.vo1d.web.entities.daybook.timetable.period.TimePeriodModel
import ru.vo1d.web.orm.entities.HasModel

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