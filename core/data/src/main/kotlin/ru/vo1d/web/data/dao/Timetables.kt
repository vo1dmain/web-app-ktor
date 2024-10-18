package ru.vo1d.web.data.dao

import ru.vo1d.web.data.filters.daybook.DatedSessionFilters
import ru.vo1d.web.data.filters.daybook.RegularSessionFilters
import ru.vo1d.web.data.filters.daybook.TimetableFilters
import ru.vo1d.web.entities.daybook.group.Group
import ru.vo1d.web.entities.daybook.group.GroupWithTypes
import ru.vo1d.web.entities.daybook.group.degree.GraduationDegree
import ru.vo1d.web.entities.daybook.group.form.EducationForm
import ru.vo1d.web.entities.daybook.group.level.GraduationLevel
import ru.vo1d.web.entities.daybook.group.type.TableType
import ru.vo1d.web.entities.daybook.timetable.Timetable
import ru.vo1d.web.entities.daybook.timetable.TimetableWithSessions
import ru.vo1d.web.entities.daybook.timetable.session.DatedSession
import ru.vo1d.web.entities.daybook.timetable.session.RegularSession
import ru.vo1d.web.entities.daybook.timetable.session.SessionType
import ru.vo1d.web.entities.daybook.timetable.session.TimetableSession
import ru.vo1d.web.entities.daybook.timetable.time.StartTime

interface TimetableDao :
    CreateDao<Int, Timetable>,
    ReadDao<Int, Timetable>,
    UpdateDao<Timetable>,
    DeleteDao<Int>,
    LinkedReadDao<Int, TimetableWithSessions<*>>,
    ListDao<Timetable>,
    FilterDao<Timetable, TimetableFilters>

interface RegularSessionDao :
    CreateDao<Int, RegularSession>,
    ReadDao<Int, RegularSession>,
    UpdateDao<RegularSession>,
    DeleteDao<Int>,
    ListDao<RegularSession>,
    FilterDao<RegularSession, RegularSessionFilters>

interface DatedSessionDao :
    CreateDao<Int, DatedSession>,
    ReadDao<Int, DatedSession>,
    UpdateDao<DatedSession>,
    DeleteDao<Int>,
    ListDao<DatedSession>,
    FilterDao<DatedSession, DatedSessionFilters>

interface TimetableRegularSessionDao :
    CreateDao<Unit, TimetableSession>,
    DeleteDao<TimetableSession>

interface TimetableDatedSessionDao :
    CreateDao<Unit, TimetableSession>,
    DeleteDao<TimetableSession>

interface GradLevelDao :
    CreateDao<String, GraduationLevel>,
    ReadDao<String, GraduationLevel>,
    UpdateDao<GraduationLevel>,
    DeleteDao<String>,
    AllDao<GraduationLevel>

interface GradDegreeDao :
    CreateDao<String, GraduationDegree>,
    ReadDao<String, GraduationDegree>,
    UpdateDao<GraduationDegree>,
    DeleteDao<String>,
    AllDao<GraduationDegree>

interface EduFormDao :
    CreateDao<String, EducationForm>,
    ReadDao<String, EducationForm>,
    UpdateDao<EducationForm>,
    DeleteDao<String>,
    AllDao<EducationForm>

interface GroupDao :
    CreateDao<String, Group>,
    ReadDao<String, GroupWithTypes>,
    UpdateDao<Group>,
    DeleteDao<String>,
    AllDao<GroupWithTypes>

interface TableTypeDao :
    CreateDao<String, TableType>,
    ReadDao<String, TableType>,
    UpdateDao<TableType>,
    DeleteDao<String>,
    AllDao<TableType>

interface SessionTypeDao :
    CreateDao<Int, SessionType>,
    ReadDao<Int, SessionType>,
    UpdateDao<SessionType>,
    DeleteDao<Int>,
    AllDao<SessionType>

interface StartTimeDao :
    CreateDao<Int, StartTime>,
    ReadDao<Int, StartTime>,
    UpdateDao<StartTime>,
    DeleteDao<Int>,
    AllDao<StartTime>