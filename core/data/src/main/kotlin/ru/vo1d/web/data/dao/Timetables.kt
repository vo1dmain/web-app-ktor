package ru.vo1d.web.data.dao

import ru.vo1d.web.data.filters.daybook.TimetableFilters
import ru.vo1d.web.entities.daybook.group.type.TableType
import ru.vo1d.web.entities.daybook.timetable.Timetable
import ru.vo1d.web.entities.daybook.timetable.session.TimetableSession

interface TimetableDao : Dao<Int, Timetable>, Pageable<Timetable>, Filterable<TimetableFilters, Timetable>

interface TimetableRegularSessionDao : Dao<Unit, TimetableSession>

interface TimetableDatedSessionDao : Dao<Unit, TimetableSession>

interface TableTypeDao : Dao<String, TableType>, AllDao<TableType>
