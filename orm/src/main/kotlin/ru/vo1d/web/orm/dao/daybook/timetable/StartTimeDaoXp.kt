package ru.vo1d.web.orm.dao.daybook.timetable

import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.update
import ru.vo1d.web.data.dao.StartTimeDao
import ru.vo1d.web.entities.daybook.timetable.period.StartTimeModel
import ru.vo1d.web.orm.dao.XpDao
import ru.vo1d.web.orm.entities.daybook.timetable.SessionStartTime
import ru.vo1d.web.orm.entities.daybook.timetable.SessionStartTimes

class StartTimeDaoXp : StartTimeDao, XpDao<StartTimeModel> {
    override suspend fun create(item: StartTimeModel) = SessionStartTimes.insertAndGetId { it.mapItem(item) }.value

    override suspend fun create(vararg items: StartTimeModel) =
        SessionStartTimes.batchInsert(items.asIterable()) { mapItem(it) }.count()

    override suspend fun read(id: Int) = SessionStartTime.findById(id)?.toModel()

    override suspend fun update(item: StartTimeModel) =
        SessionStartTimes.update({ SessionStartTimes.id eq item.id }) { it.mapItem(item) }

    override suspend fun delete(id: Int) = SessionStartTimes.deleteWhere { SessionStartTimes.id eq id }

    override suspend fun all() = SessionStartTime.all().map(SessionStartTime::toModel)

    override fun UpdateBuilder<Int>.mapItem(item: StartTimeModel) {
        this[SessionStartTimes.time] = item.time
        item.timeZone?.let { this[SessionStartTimes.timeZone] = it.id }
    }
}