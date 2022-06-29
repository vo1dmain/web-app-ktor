package ru.vo1d.web.app.exposed.orm.daybook.group

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.ReferenceOption.CASCADE
import ru.vo1d.web.app.exposed.orm.HasDto
import ru.vo1d.web.app.exposed.orm.daybook.timetable.Timetables
import ru.vo1d.web.entities.daybook.group.GroupDto

object Groups : IdTable<String>() {
    override val id = varchar("id", 16).uniqueIndex().entityId()

    val levelId = reference("levelId", GraduationLevels, CASCADE, CASCADE)
    val degreeId = optReference("degreeId", GraduationDegrees, CASCADE, CASCADE)
    val formId = reference("formId", EducationForms, CASCADE, CASCADE)
}

sealed class Group(id: EntityID<String>) : Entity<String>(id) {
    companion object : EntityClass<String, Group>(Groups)

    val levelId by Groups.levelId
    val degreeId by Groups.degreeId
    val formId by Groups.formId
}

class GroupWithTypes(id: EntityID<String>) : Group(id), HasDto<GroupDto> {
    companion object : EntityClass<String, GroupWithTypes>(Groups)

    val types by TableType referrersOn Timetables.typeId

    override fun toDto() =
        GroupDto(id.value, levelId.value, degreeId?.value, formId.value, types.map { it.id.value })
}