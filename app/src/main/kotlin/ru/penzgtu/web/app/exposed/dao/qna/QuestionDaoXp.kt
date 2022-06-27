package ru.penzgtu.web.app.exposed.dao.qna

import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.update
import ru.penzgtu.web.app.data.dao.QuestionDao
import ru.penzgtu.web.app.exposed.orm.qna.Question
import ru.penzgtu.web.app.exposed.orm.qna.Questions
import ru.penzgtu.web.entities.qna.question.QuestionModel

class QuestionDaoXp : QuestionDao {
    override suspend fun create(item: QuestionModel): Int {
        return Questions.insertAndGetId {
            it[theme] = item.theme
            it[body] = item.body
            it[acceptorId] = item.acceptorId
            it[email] = item.email
            it[dateTime] = item.dateTime
        }.value
    }

    override suspend fun read(id: Int): QuestionModel? {
        return Question.findById(id)?.model()
    }

    override suspend fun update(item: QuestionModel): Int {
        return Questions.update({ Questions.id eq item.id }) {
            it[theme] = item.theme
            it[body] = item.body
            it[acceptorId] = item.acceptorId
            it[email] = item.email
            it[dateTime] = item.dateTime
        }
    }

    override suspend fun delete(id: Int): Int {
        return Questions.deleteWhere {
            Questions.id eq id
        }
    }

    override suspend fun list(offset: Long, limit: Int): List<QuestionModel> {
        return Question.all().limit(limit, offset).map(Question::model)
    }
}