package ru.vo1d.web.orm.entities.daybook.timetable

import kotlinx.datetime.TimeZone
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import ru.vo1d.web.entities.daybook.timetable.period.TimePeriodModel
import ru.vo1d.web.orm.entities.HasModel
import ru.vo1d.web.orm.utils.time.time

object TimePeriods : IntIdTable() {
    val start = time("start")
    val end = time("end")
    val timeZone = varchar("timeZone", 32).default(TimeZone.currentSystemDefault().id)
}

class TimePeriod(id: EntityID<Int>) : IntEntity(id), HasModel<TimePeriodModel> {
    companion object : IntEntityClass<TimePeriod>(TimePeriods)

    val start by TimePeriods.start
    val end by TimePeriods.end
    val timeZone by TimePeriods.timeZone

    override fun toModel() = TimePeriodModel(id.value, start, end, TimeZone.of(timeZone))
}