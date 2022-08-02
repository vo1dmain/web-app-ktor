package ru.vo1d.web.orm.entities.daybook.group

import org.jetbrains.exposed.dao.id.EntityID
import ru.vo1d.web.entities.daybook.group.level.GraduationLevel
import ru.vo1d.web.orm.entities.HasModel
import ru.vo1d.web.orm.utils.tables.StringEntity
import ru.vo1d.web.orm.utils.tables.StringEntityClass
import ru.vo1d.web.orm.utils.tables.StringIdTable

object GraduationLevels : StringIdTable(idColLength = 16) {
    val title = varchar("title", 64)
}

class GraduationLevelEntity(id: EntityID<String>) : StringEntity(id), HasModel<GraduationLevel> {
    companion object : StringEntityClass<GraduationLevelEntity>(GraduationLevels)

    val title by GraduationLevels.title

    override fun toModel() = GraduationLevel(id.value, title)
}