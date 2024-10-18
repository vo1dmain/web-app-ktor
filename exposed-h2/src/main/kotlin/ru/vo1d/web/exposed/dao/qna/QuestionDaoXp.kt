package ru.vo1d.web.exposed.dao.qna

import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.update
import ru.vo1d.web.data.dao.QuestionDao
import ru.vo1d.web.entities.qna.question.Question
import ru.vo1d.web.exposed.dao.XpDao
import ru.vo1d.web.exposed.entities.qna.QuestionEntity
import ru.vo1d.web.exposed.entities.qna.Questions

class QuestionDaoXp : QuestionDao, XpDao<Question> {
    override suspend fun create(item: Question) =
        Questions.insertAndGetId { it.mapItem(item) }.value

    override suspend fun create(vararg items: Question) =
        Questions.batchInsert(items.asIterable()) { mapItem(it) }.count()

    override suspend fun read(id: Int) =
        QuestionEntity.findById(id)?.toModel()

    override suspend fun update(item: Question) =
        Questions.update({ Questions.id eq item.id }) { it.mapItem(item) }

    override suspend fun delete(id: Int) =
        Questions.deleteWhere { Questions.id eq id }

    override suspend fun list(offset: Long, limit: Int) =
        QuestionEntity.all()
            .limit(limit)
            .offset(offset)
            .map(QuestionEntity::toModel)

    override fun UpdateBuilder<*>.mapItem(item: Question) {
        this[Questions.theme] = item.theme
        this[Questions.body] = item.body
        this[Questions.acceptorId] = item.acceptorId
        this[Questions.email] = item.email
        item.dateTime?.let { this[Questions.dateTime] = it }
        item.timeZone?.let { this[Questions.timeZone] = it.id }
    }
}