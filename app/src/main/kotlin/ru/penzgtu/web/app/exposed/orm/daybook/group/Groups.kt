package ru.penzgtu.web.app.exposed.orm.daybook.group

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.ReferenceOption.CASCADE
import ru.penzgtu.web.app.exposed.orm.HasModel
import ru.penzgtu.web.app.exposed.orm.daybook.timetable.Timetables
import ru.penzgtu.web.entities.daybook.group.GroupModel

object Groups : IdTable<String>() {
    override val id = varchar("id", 8).entityId()

    val levelId = reference("levelId", GraduationLevels, CASCADE, CASCADE)
    val formId = reference("formId", EducationForms, CASCADE, CASCADE)
}

class Group(id: EntityID<String>) : Entity<String>(id), HasModel<GroupModel> {
    companion object : EntityClass<String, Group>(Groups)

    val levelId by Groups.levelId
    val formId by Groups.formId
    val types by TableType referrersOn Timetables.typeId

    override fun toModel() = GroupModel(id.value, levelId.value, formId.value, types.map { it.id.value })
}