package ru.vo1d.web.exposed.entities.daybook.timetable

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.id.EntityID
import ru.vo1d.web.entities.daybook.timetable.session.Session

internal abstract class SessionEntity<out T : Session>(id: EntityID<Int>) : IntEntity(id)