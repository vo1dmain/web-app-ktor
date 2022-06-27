package ru.penzgtu.web.app.exposed.dao.qna

import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.update
import ru.penzgtu.web.app.data.dao.AnswerDao
import ru.penzgtu.web.app.exposed.orm.qna.Answer
import ru.penzgtu.web.app.exposed.orm.qna.Answers
import ru.penzgtu.web.entities.qna.answer.AnswerModel

class AnswerDaoXp : AnswerDao {
    override suspend fun create(item: AnswerModel): Int {
        return Answers.insertAndGetId {
            it[questionId] = item.questionId
            it[body] = item.body
            it[dateTime] = item.dateTime
        }.value
    }

    override suspend fun read(id: Int): AnswerModel? {
        return Answer.findById(id)?.model()
    }

    override suspend fun update(item: AnswerModel): Int {
        return Answers.update({ Answers.id eq item.id }) {
            it[questionId] = item.questionId
            it[body] = item.body
            it[dateTime] = item.dateTime
        }
    }

    override suspend fun delete(id: Int): Int {
        return Answers.deleteWhere {
            Answers.id eq id
        }
    }

    override suspend fun list(offset: Long, limit: Int): List<AnswerModel> {
        return Answer.all().limit(limit, offset).map(Answer::model)
    }
}