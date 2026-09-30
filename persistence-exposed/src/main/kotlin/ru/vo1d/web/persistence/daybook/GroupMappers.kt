package ru.vo1d.web.persistence.daybook

import org.jetbrains.exposed.v1.core.statements.UpdateBuilder
import ru.vo1d.web.domain.daybook.group.*

internal fun UpdateBuilder<*>.mapItem(item: EducationForm) {
    this[EducationForms.id] = item.id
    this[EducationForms.title] = item.title
}

internal fun UpdateBuilder<*>.mapItem(item: GraduationDegree) {
    this[GraduationDegrees.id] = item.id
    this[GraduationDegrees.title] = item.title
}

internal fun UpdateBuilder<*>.mapItem(item: GraduationLevel) {
    this[GraduationLevels.id] = item.id
    this[GraduationLevels.title] = item.title
}

internal fun UpdateBuilder<*>.mapItem(item: Group) {
    this[Groups.id] = item.code
    this[Groups.levelId] = item.levelId
    this[Groups.degreeId] = item.degreeId
    this[Groups.formId] = item.formId
}

internal fun UpdateBuilder<*>.mapItem(item: TableType) {
    this[TableTypes.id] = item.id
    this[TableTypes.title] = item.title
}

internal fun EducationFormEntity.toDomain() = EducationForm(id.value, title)

internal fun GraduationDegreeEntity.toDomain() = GraduationDegree(id.value, title)

internal fun GraduationLevelEntity.toDomain() = GraduationLevel(id.value, title)

internal fun GroupEntity.toDomain() = Group(id.value, levelId.value, degreeId?.value, formId.value, types.map { it.id.value })

internal fun TableTypeEntity.toDomain() = TableType(id.value, title)