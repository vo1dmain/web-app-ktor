package ru.vo1d.web.exposed.dao.qna

import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.core.inList
import org.jetbrains.exposed.v1.jdbc.batchInsert
import org.jetbrains.exposed.v1.jdbc.deleteWhere
import org.jetbrains.exposed.v1.jdbc.insertAndGetId
import org.jetbrains.exposed.v1.jdbc.update
import ru.vo1d.web.data.dao.QuestionDao
import ru.vo1d.web.entities.qna.question.Question
import ru.vo1d.web.exposed.context.DbContext
import ru.vo1d.web.exposed.dao.XpDao
import ru.vo1d.web.exposed.entities.qna.QuestionEntity
import ru.vo1d.web.exposed.entities.qna.Questions
import ru.vo1d.web.exposed.mappers.mapItem
import ru.vo1d.web.exposed.mappers.toDomain

class QuestionDaoXp(ctx: DbContext) : XpDao(ctx.qna), QuestionDao {
    override suspend fun create(item: Question): Int = query {
        Questions.insertAndGetId { it.mapItem(item) }.value
    }

    override suspend fun create(vararg items: Question): Int = query {
        Questions.batchInsert(items.asIterable()) { mapItem(it) }.count()
    }

    override suspend fun read(id: Int): Question? = query {
        QuestionEntity.findById(id)?.toDomain()
    }

    override suspend fun update(item: Question): Int = query {
        Questions.update({ Questions.id eq item.id }) { it.mapItem(item) }
    }

    override suspend fun delete(vararg items: Question): Int = query {
        Questions.deleteWhere { Questions.id inList items.mapNotNull { it.id } }
    }

    override suspend fun page(offset: Long, limit: Int): List<Question> = query {
        QuestionEntity.all()
            .limit(limit)
            .offset(offset)
            .map(QuestionEntity::toDomain)
    }
}
