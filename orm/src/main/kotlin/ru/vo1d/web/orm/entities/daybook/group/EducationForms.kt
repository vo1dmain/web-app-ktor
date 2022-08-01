package ru.vo1d.web.orm.entities.daybook.group

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import ru.vo1d.web.entities.daybook.group.form.EducationForm
import ru.vo1d.web.orm.entities.HasModel

object EducationForms : IdTable<String>() {
    override val id = varchar("id", 16).uniqueIndex().entityId()
    val title = varchar("title", 32)
}

class EducationFormEntity(id: EntityID<String>) : Entity<String>(id), HasModel<EducationForm> {
    companion object : EntityClass<String, EducationFormEntity>(EducationForms)

    val title by EducationForms.title

    override fun toModel() = EducationForm(id.value, title)
}