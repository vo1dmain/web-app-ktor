package ru.vo1d.web.orm.entities.daybook.group

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import ru.vo1d.web.entities.daybook.group.level.GraduationLevel
import ru.vo1d.web.orm.entities.HasModel

object GraduationLevels : IdTable<String>() {
    override val id = varchar("id", 16).uniqueIndex().entityId()
    val title = varchar("title", 64)
}

class GraduationLevelEntity(id: EntityID<String>) : Entity<String>(id), HasModel<GraduationLevel> {
    companion object : EntityClass<String, GraduationLevelEntity>(GraduationLevels)

    val title by GraduationLevels.title

    override fun toModel() = GraduationLevel(id.value, title)
}