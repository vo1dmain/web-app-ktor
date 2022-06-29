package ru.vo1d.web.app.exposed.orm.daybook.group

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.ReferenceOption.CASCADE
import ru.vo1d.web.app.exposed.orm.HasModel
import ru.vo1d.web.entities.daybook.group.GroupModel

object Groups : IdTable<String>() {
    override val id = varchar("id", 16).uniqueIndex().entityId()

    val levelId = reference("levelId", GraduationLevels, CASCADE, CASCADE)
    val degreeId = optReference("degreeId", GraduationDegrees, CASCADE, CASCADE)
    val formId = reference("formId", EducationForms, CASCADE, CASCADE)
}

class Group(id: EntityID<String>) : Entity<String>(id), HasModel<GroupModel> {
    companion object : EntityClass<String, Group>(Groups)

    val levelId by Groups.levelId
    val degreeId by Groups.degreeId
    val formId by Groups.formId
//    val types by TableType referrersOn Timetables.typeId

    override fun toModel() =
        GroupModel(id.value, levelId.value, degreeId?.value, formId.value, emptyList())
}