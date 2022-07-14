package ru.vo1d.web.data.repos

import ru.vo1d.web.data.filters.daybook.SessionFilters
import ru.vo1d.web.data.filters.daybook.TimetableFilters
import ru.vo1d.web.entities.daybook.Meta
import ru.vo1d.web.entities.daybook.group.GroupDto
import ru.vo1d.web.entities.daybook.group.degree.GradDegreeModel
import ru.vo1d.web.entities.daybook.group.form.EduFormModel
import ru.vo1d.web.entities.daybook.group.level.GradLevelModel
import ru.vo1d.web.entities.daybook.group.type.TableTypeModel
import ru.vo1d.web.entities.daybook.timetable.TimetableDto
import ru.vo1d.web.entities.daybook.timetable.TimetableModel
import ru.vo1d.web.entities.daybook.timetable.period.TimePeriodModel
import ru.vo1d.web.entities.daybook.timetable.session.SessionModel
import ru.vo1d.web.entities.daybook.timetable.session.SessionTypeModel
import ru.vo1d.web.entities.daybook.timetable.week.WeekOptionModel

interface DaybookRepo : ListRepo {
    suspend fun meta(): Meta

    suspend fun levels(): List<GradLevelModel>

    suspend fun degrees(): List<GradDegreeModel>

    suspend fun forms(): List<EduFormModel>

    suspend fun tableTypes(): List<TableTypeModel>

    suspend fun groups(): List<GroupDto>

    suspend fun periods(): List<TimePeriodModel>

    suspend fun sessionTypes(): List<SessionTypeModel>

    suspend fun weekOptions(): List<WeekOptionModel>



    suspend fun timetables(page: Int?, filtersProducer: TimetableFilters.() -> Unit): List<TimetableModel>

    suspend fun timetable(id: Int): TimetableDto?

    suspend fun addTimetable(item: TimetableModel): Int



    suspend fun sessions(page: Int?, filtersProducer: SessionFilters.() -> Unit): List<SessionModel>

    suspend fun addSession(item: SessionModel): Int
}