package ru.vo1d.web.exposed.entities.daybook.timetable

import kotlinx.datetime.TimeZone
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass
import org.jetbrains.exposed.v1.datetime.time

internal object SessionStartTimes : IntIdTable() {
    val time = time("time").uniqueIndex()
    val timeZone = varchar("timeZone", 32).default(TimeZone.currentSystemDefault().id)
}

internal class StartTimeEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<StartTimeEntity>(SessionStartTimes)

    val time by SessionStartTimes.time
    val timeZone by SessionStartTimes.timeZone
}