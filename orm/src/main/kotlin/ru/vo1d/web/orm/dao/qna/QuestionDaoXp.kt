package ru.vo1d.web.orm.dao.qna

import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.update
import ru.vo1d.web.data.dao.QuestionDao
import ru.vo1d.web.entities.qna.question.QuestionModel
import ru.vo1d.web.orm.dao.XpDao
import ru.vo1d.web.orm.entities.qna.Question
import ru.vo1d.web.orm.entities.qna.Questions

class QuestionDaoXp : QuestionDao, XpDao<QuestionModel> {
    override suspend fun create(item: QuestionModel) = Questions.insertAndGetId { it.mapItem(item) }.value

    override suspend fun create(vararg items: QuestionModel) =
        Questions.batchInsert(items.asIterable()) { mapItem(it) }.count()

    override suspend fun read(id: Int) = Question.findById(id)?.toModel()

    override suspend fun update(item: QuestionModel) =
        Questions.update({ Questions.id eq item.id }) { it.mapItem(item) }

    override suspend fun delete(id: Int) = Questions.deleteWhere { Questions.id eq id }

    override suspend fun list(offset: Long, limit: Int) = Question.all().limit(limit, offset).map(Question::toModel)

    override fun UpdateBuilder<Int>.mapItem(item: QuestionModel) {
        this[Questions.theme] = item.theme
        this[Questions.body] = item.body
        this[Questions.acceptorId] = item.acceptorId
        this[Questions.email] = item.email
        item.dateTime?.let { this[Questions.dateTime] = it }
        item.timeZone?.let { this[Questions.timeZone] = it.id }
    }
}