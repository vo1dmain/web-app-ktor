package ru.vo1d.web.orm.dao.daybook.group

import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.update
import ru.vo1d.web.data.dao.GradDegreeDao
import ru.vo1d.web.entities.daybook.group.degree.GradDegreeModel
import ru.vo1d.web.orm.dao.XpDao
import ru.vo1d.web.orm.entities.daybook.group.GraduationDegree
import ru.vo1d.web.orm.entities.daybook.group.GraduationDegrees

class GradDegreeDaoXp : GradDegreeDao, XpDao<GradDegreeModel> {
    override suspend fun create(item: GradDegreeModel) = GraduationDegrees.insertAndGetId { it.mapItem(item) }.value

    override suspend fun create(vararg items: GradDegreeModel) =
        GraduationDegrees.batchInsert(items.asIterable()) { mapItem(it) }.count()

    override suspend fun read(id: String) = GraduationDegree.findById(id)?.toModel()

    override suspend fun update(item: GradDegreeModel) =
        GraduationDegrees.update({ GraduationDegrees.id eq item.id }) { it.mapItem(item) }

    override suspend fun delete(id: String) = GraduationDegrees.deleteWhere { GraduationDegrees.id eq id }

    override suspend fun all() = GraduationDegree.all().map(GraduationDegree::toModel)

    override fun UpdateBuilder<Int>.mapItem(item: GradDegreeModel) {
        this[GraduationDegrees.id] = item.id
        this[GraduationDegrees.title] = item.title
    }
}