package ru.vo1d.web.persistence.entities.daybook.timetable

import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.IntEntity
import ru.vo1d.web.domain.daybook.timetable.session.Session

internal abstract class SessionEntity<out T : Session>(id: EntityID<Int>) : IntEntity(id)