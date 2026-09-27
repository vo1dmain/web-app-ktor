package ru.vo1d.web.domain.dao

import ru.vo1d.web.domain.filters.daybook.DatedSessionFilters
import ru.vo1d.web.domain.filters.daybook.RegularSessionFilters
import ru.vo1d.web.domain.daybook.timetable.session.DatedSession
import ru.vo1d.web.domain.daybook.timetable.session.RegularSession
import ru.vo1d.web.domain.daybook.timetable.session.SessionType

interface SessionTypeDao : Dao<Int, SessionType>, AllDao<SessionType>

interface RegularSessionDao : Dao<Int, RegularSession>, Pageable<RegularSession>,
    Filterable<RegularSessionFilters, RegularSession>

interface DatedSessionDao : Dao<Int, DatedSession>, Pageable<DatedSession>,
    Filterable<DatedSessionFilters, DatedSession>