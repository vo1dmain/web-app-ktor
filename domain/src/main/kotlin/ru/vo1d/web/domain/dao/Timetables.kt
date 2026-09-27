package ru.vo1d.web.domain.dao

import ru.vo1d.web.domain.filters.daybook.TimetableFilters
import ru.vo1d.web.domain.daybook.group.TableType
import ru.vo1d.web.domain.daybook.timetable.Timetable
import ru.vo1d.web.domain.daybook.timetable.session.TimetableSession

interface TimetableDao : Dao<Int, Timetable>, Pageable<Timetable>, Filterable<TimetableFilters, Timetable>

interface TimetableRegularSessionDao : Dao<Unit, TimetableSession>

interface TimetableDatedSessionDao : Dao<Unit, TimetableSession>

interface TableTypeDao : Dao<String, TableType>, AllDao<TableType>
