package ru.vo1d.web.app.exposed.dao.daybook.group

import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.update
import ru.vo1d.web.app.data.dao.GradDegreeDao
import ru.vo1d.web.app.exposed.orm.daybook.group.GraduationDegree
import ru.vo1d.web.app.exposed.orm.daybook.group.GraduationDegrees
import ru.vo1d.web.entities.daybook.group.degree.GradDegreeModel

class GradDegreeDaoXp : GradDegreeDao {
    override suspend fun create(item: GradDegreeModel) = GraduationDegrees.insertAndGetId {
        it[title] = item.title
    }.value

    override suspend fun read(id: String) = GraduationDegree.findById(id)?.toModel()

    override suspend fun update(item: GradDegreeModel) = GraduationDegrees.update({ GraduationDegrees.id eq item.id }) {
        it[title] = item.title
    }

    override suspend fun delete(id: String) = GraduationDegrees.deleteWhere { GraduationDegrees.id eq id }

    override suspend fun all() = GraduationDegree.all().map(GraduationDegree::toModel)
}