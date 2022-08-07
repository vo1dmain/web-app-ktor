package ru.vo1d.web.exposed.entities.daybook.timetable

import ru.vo1d.web.entities.daybook.timetable.session.Session
import ru.vo1d.web.exposed.entities.HasModel

sealed interface SessionEntity<out T : Session> : HasModel<T>