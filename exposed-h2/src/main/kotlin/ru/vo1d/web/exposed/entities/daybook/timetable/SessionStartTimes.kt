package ru.vo1d.web.exposed.entities.daybook.timetable

import kotlinx.datetime.TimeZone
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import ru.vo1d.web.entities.daybook.timetable.time.StartTime
import ru.vo1d.web.exposed.entities.HasModel
import ru.vo1d.web.exposed.utils.time.time

object SessionStartTimes : IntIdTable() {
    val time = time("time").uniqueIndex()
    val timeZone = varchar("timeZone", 32).default(TimeZone.currentSystemDefault().id)
}

class SessionStartTime(id: EntityID<Int>) : IntEntity(id), HasModel<StartTime> {
    companion object : IntEntityClass<SessionStartTime>(SessionStartTimes)

    val time by SessionStartTimes.time
    val timeZone by SessionStartTimes.timeZone

    override fun toModel() = StartTime(id.value, time, TimeZone.of(timeZone))
}