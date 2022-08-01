package ru.vo1d.web.orm.entities.daybook.group

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.ReferenceOption.CASCADE
import ru.vo1d.web.orm.entities.HasModel
import ru.vo1d.web.orm.entities.daybook.timetable.Timetables
import ru.vo1d.web.entities.daybook.group.GroupWithTypes

object Groups : IdTable<String>() {
    override val id = varchar("id", 16).uniqueIndex().entityId()

    val levelId = reference("levelId", GraduationLevels, CASCADE, CASCADE)
    val degreeId = optReference("degreeId", GraduationDegrees, CASCADE, CASCADE)
    val formId = reference("formId", EducationForms, CASCADE, CASCADE)
}

open class GroupEntity(id: EntityID<String>) : Entity<String>(id) {
    companion object : EntityClass<String, GroupEntity>(Groups)

    val levelId by Groups.levelId
    val degreeId by Groups.degreeId
    val formId by Groups.formId
}

class GroupWithTypesEntity(id: EntityID<String>) : GroupEntity(id), HasModel<GroupWithTypes> {
    companion object : EntityClass<String, GroupWithTypesEntity>(Groups)

    val types by TableTypeEntity via Timetables

    override fun toModel() =
        GroupWithTypes(id.value, levelId.value, degreeId?.value, formId.value, types.map { it.id.value })
}