package ru.penzgtu.web.app.exposed.dao.daybook

import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.update
import ru.penzgtu.web.app.data.dao.GraduationLevelDao
import ru.penzgtu.web.app.exposed.orm.daybook.group.GraduationLevel
import ru.penzgtu.web.app.exposed.orm.daybook.group.GraduationLevels
import ru.penzgtu.web.entities.daybook.group.level.GraduationLevelModel

class GraduationLevelDaoXp : GraduationLevelDao {
    override suspend fun create(item: GraduationLevelModel): String {
        return GraduationLevels.insertAndGetId {
            it[id] = item.id
            it[title] = item.title
        }.value
    }

    override suspend fun read(id: String): GraduationLevelModel? {
        return GraduationLevel.findById(id)?.toModel()
    }

    override suspend fun update(item: GraduationLevelModel): Int {
        return GraduationLevels.update({ GraduationLevels.id eq item.id }) {
            it[title] = item.title
        }
    }

    override suspend fun delete(id: String): Int {
        return GraduationLevels.deleteWhere {
            GraduationLevels.id eq id
        }
    }

    override suspend fun all(): List<GraduationLevelModel> {
        return GraduationLevel.all().map(GraduationLevel::toModel)
    }
}