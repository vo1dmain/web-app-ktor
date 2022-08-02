package ru.vo1d.web.orm.entities.daybook.group

import org.jetbrains.exposed.dao.id.EntityID
import ru.vo1d.web.entities.daybook.group.degree.GraduationDegree
import ru.vo1d.web.orm.entities.HasModel
import ru.vo1d.web.orm.utils.tables.StringEntity
import ru.vo1d.web.orm.utils.tables.StringEntityClass
import ru.vo1d.web.orm.utils.tables.StringIdTable

object GraduationDegrees : StringIdTable(idColLength = 16) {
    val title = varchar("title", 16)
}

class GraduationDegreeEntity(id: EntityID<String>) : StringEntity(id), HasModel<GraduationDegree> {
    companion object : StringEntityClass<GraduationDegreeEntity>(GraduationDegrees)

    val title by GraduationDegrees.title

    override fun toModel() = GraduationDegree(id.value, title)
}