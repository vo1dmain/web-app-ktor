package ru.vo1d.web.orm.entities.daybook.timetable

import ru.vo1d.web.entities.daybook.timetable.session.SessionModel
import ru.vo1d.web.orm.entities.HasModel

sealed interface SessionEntity<out T : SessionModel> : HasModel<T>