package ru.vo1d.web.data.dao

import ru.vo1d.web.data.filters.daybook.DatedSessionFilters
import ru.vo1d.web.data.filters.daybook.RegularSessionFilters
import ru.vo1d.web.entities.daybook.timetable.session.DatedSession
import ru.vo1d.web.entities.daybook.timetable.session.RegularSession
import ru.vo1d.web.entities.daybook.timetable.session.SessionType
import ru.vo1d.web.entities.daybook.timetable.time.StartTime

interface SessionTypeDao : Dao<Int, SessionType>, AllDao<SessionType>

interface StartTimeDao : Dao<Int, StartTime>, AllDao<StartTime>

interface RegularSessionDao : Dao<Int, RegularSession>, Pageable<RegularSession>,
    Filterable<RegularSessionFilters, RegularSession>

interface DatedSessionDao : Dao<Int, DatedSession>, Pageable<DatedSession>,
    Filterable<DatedSessionFilters, DatedSession>