package ru.vo1d.web.persistence.entities.daybook.group

import org.jetbrains.exposed.v1.core.dao.id.EntityID
import ru.vo1d.web.persistence.tables.StringEntity
import ru.vo1d.web.persistence.tables.StringEntityClass
import ru.vo1d.web.persistence.tables.StringIdTable

internal object GraduationLevels : StringIdTable(idColumnLength = 16) {
    val title = varchar("title", 64)
}

internal class GraduationLevelEntity(id: EntityID<String>) : StringEntity(id) {
    companion object : StringEntityClass<GraduationLevelEntity>(GraduationLevels)

    val title by GraduationLevels.title
}