package ru.penzgtu.web.app.data.dao

import ru.penzgtu.web.app.data.entities.timetables.main.Timetable
import ru.penzgtu.web.app.data.entities.timetables.main.TimetableView

interface TimetableDao : CrudDao<Timetable, Int>, ListDao<TimetableView>, FilterDao<TimetableView>